package controller.builder;
/*
 * $Id: Builder.java 190 2014-07-24 08:45:25Z pspeed42 $
 * 
 * Copyright (c) 2014, Simsilica, LLC
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions 
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright 
 *    notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright 
 *    notice, this list of conditions and the following disclaimer in 
 *    the documentation and/or other materials provided with the 
 *    distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of its 
 *    contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT 
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS 
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE 
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR 
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED 
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *  A set of worker threads and management for building
 *  things in the background with proper hooks for
 *  resolving them on a specific thread (say the rendering
 *  thread, for example).
 *
 *  @author    Paul Speed
 */
public class Builder {

    static Logger log = LoggerFactory.getLogger(Builder.class);

    private static AtomicLong instanceCount = new AtomicLong();

    private String name;

    private Map<BuilderReference,PrioritizedRef> refMap = new ConcurrentHashMap<BuilderReference,PrioritizedRef>();
    private PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<Runnable>();
    private ConcurrentLinkedQueue<PrioritizedRef> pausedItems = new ConcurrentLinkedQueue<PrioritizedRef>();
    private AtomicInteger pausedCount = new AtomicInteger();

    private PriorityBlockingQueue<PrioritizedRef> done = new PriorityBlockingQueue<PrioritizedRef>();

    private ThreadPoolExecutor executor;


    public Builder( String name, int poolSize ) {        
        this.name = name;
        this.executor = new ThreadPoolExecutor( poolSize, poolSize, 0L, TimeUnit.MILLISECONDS,
                                                (PriorityBlockingQueue<Runnable>)queue,
                                                new BuilderThreadFactory() );
    }
 
    public int getPendingCount() {
        return queue.size();
    }
    
    public int getManagedCount() {
        return refMap.size();
    }
 
    public boolean isShutdown() {
        return executor.isShutdown();
    }
 
    /**
     *  Queues the specified reference up for building and begins to
     *  manage its lifecycle.  If the reference is already being managed
     *  then it is marked for rebuild if it isn't already awaiting a build.
     *  Once the reference is built by calling its build() method on a
     *  pooled thread, the reference is queued waiting for apply().  It is
     *  up to the caller to periodically call applyUpdates() for references
     *  to be applied.
     *  References are managed indefinitely until Builder.release() is
     *  called.
     */   
    public void build( BuilderReference ref ) {
 
        if( log.isTraceEnabled() ) {
            log.trace("build(" + ref + ")" );
        } 
        // See if we already have a reference for this
        PrioritizedRef pr = refMap.get(ref);
        if( pr == null ) {
            // build(ref) is a single threaded method so there is
            // no race condition here.  The only way a reference gets
            // removed from the map is on applyUpdates() which is also on
            // the same thread.
        
            // Need to create the new reference and track it 
            pr = new PrioritizedRef( ref );
            
            // Always put it in the reference map before enqueueing
            // otherwise there could be a race where the thread processes
            // the item before we've added it to the map.       
            refMap.put( ref, pr );
        }               

        pr.markForBuild();
    }
 
    protected void execute( PrioritizedRef pr ) {
        if( pausedCount.get() == 0 ) {
            if( log.isTraceEnabled() ) {
                log.trace("-executing:" + pr.ref);
            }
            executor.execute(pr);
        } else {
            if( log.isTraceEnabled() ) {
                log.trace("-adding to paused items:" + pr.ref);
            }
            // Just add it to the paused queue
            pausedItems.add( pr );
        }
    }

    protected boolean cancel( PrioritizedRef pr ) {
        // Try to make sure it doesn't get executed
        if( pausedCount.get() == 0 ) {
            if( executor.remove(pr) ) {
                if( log.isDebugEnabled() ) {
                    log.debug( "canceled exec:" + pr.ref );        
                }
                return true;
            } 
        } else {
            // Just remove it from the paused queue
            if( pausedItems.remove(pr) ) {
                if( log.isDebugEnabled() ) {
                    log.debug( "canceled paused exec:" + pr.ref );        
                }
                return true;             
            }
        }
        // Wasn't even pending but may be running already
        return false;
    }
 
    /**
     *  Cancels any pending processing and queues the reference
     *  up for releasing.  The reference's release() method will
     *  be called during applyUpdates() based on its priority.
     */   
    public void release( BuilderReference ref ) {

        if( log.isTraceEnabled() ) {
            log.trace("release(" + ref + ")" );
        } 
        // Leave the reference in the map until it is
        // fully released.  This allows us to track double-releases.
        PrioritizedRef pr = refMap.get(ref);
        if( pr == null ) {
            throw new IllegalArgumentException("Unrecognized reference:" + ref);
        }
        
        // Else it's being processed or it's already in the done
        // pile.  So we'll just force its state to release
        pr.markForRelease();
    }
 
    /**
     *  Returns true if the specified reference is being managed
     *  by this builder.
     */   
    public boolean isManaged( BuilderReference ref ) {
        return refMap.containsKey(ref);
    }
 
    /**
     *  Returns true if this builder is currently paused.  A
     *  paused builder stops executing any new pending processes
     *  until unpaused.  This is useful for resetting priorities.
     */
    public boolean isPaused() {
        return pausedCount.get() > 0;
    }
    
    /**
     *  Pauses the builder which tops executing any new pending processes
     *  until unpaused.  This is useful for resetting priorities.
     */
    public void pause() {
        log.trace("pause()");
        if( pausedCount.addAndGet(1) > 1 ) {
            log.trace("already paused");
            return;
        }

        // Shuffle all of the pending items from pending to paused.
        PrioritizedRef ref;
        while( (ref = (PrioritizedRef)queue.poll()) != null ) {
            pausedItems.add(ref);
        } 
    }
 
    /**
     *  Begins processing pending references, resorting the queue
     *  based on the latest priorities.
     */   
    public void resume() {
        log.trace("resume()");
        int i = pausedCount.decrementAndGet();     
        if( i < 0 ) {
            throw new IllegalStateException("Resumed without pause.");        
        } else if( i > 0 ) {
            log.trace(i + " pending pauses remain");
            return;            
        }
        
        
        // Refresh priority and re-add paused items
        // We'll presort them into a temporary priority queue
        // so that they go into the queue in order.  Otherwise, we may
        // end up crunching on some out-of-order items right away and
        // it looks strange.  The executor's queue wouldn't have had
        // a chance to sort them any better yet because better candidates
        // haven't been added yet.
        if( pausedItems.isEmpty() ) {
            return;
        }
        PriorityQueue<PrioritizedRef> temp = new PriorityQueue<PrioritizedRef>(pausedItems.size()); 
        PrioritizedRef ref;
        while( (ref = pausedItems.poll()) != null ) {
            ref.resetPriority();
            temp.add(ref);
        }

        // Now execute them for real
        while( (ref = temp.poll()) != null ) {
            ref.resetPriority();
            executor.execute(ref);
        }
    }
 
    /**
     *  Causes 'max' number of done references to have their apply() methods
     *  called and/or setup for reprocessing or release().  It is necessary
     *  for the user to call this periodically to apply changes that have
     *  been built by the BuilderReference objects being managed.
     *  The BuilderState is a convenient way of letting this happen automatically
     *  as applyUpdates() will be called once per frame.
     *  At the time this is called, 'max' elements are drained from the done
     *  queue for processing.  If any higher priority references finish during
     *  this processing they still won't be applied until the next time applyUpdates()
     *  is called.
     *  Keep 'max' relatively small to avoid frame drops but keep it high enough
     *  that changes get applied in a timely manner.  This will be application
     *  dependent.
     */   
    public int applyUpdates( int max ) {
        if( done.isEmpty() ) {
            return 0;
        }
        
        ArrayList<PrioritizedRef> temp = new ArrayList<PrioritizedRef>();
        int count = done.drainTo(temp, max);
        int processed = 0;
        
        for( PrioritizedRef pr : temp ) {
            if( log.isTraceEnabled() ) {
                log.trace("Applying updates for:" + pr.ref + "  state:" + pr.state.get());
            }            
            pr.apply();
            processed++;
        }
        return processed;        
    }
 
    /**
     *  Shuts down the thread pool and stops accepting new tasks for execution.
     *  applyUpdates() can stil be called but no new references will be completed
     *  after the currently in-process references are done.
     */   
    public void shutdown() {
        executor.shutdownNow();

        if( log.isTraceEnabled() ) {
            log.trace("Builder unreleased references:" + refMap.keySet());
        }        
    }
    
    protected void handleError( Throwable t ) {
        log.error( "Uncaught exception in worker thread", t );
    }
 
    protected enum State {
        Pending, Processing, Done, Release, Reprocess, Idle
    }
 
    protected class PrioritizedRef implements Runnable, Comparable<PrioritizedRef> {
 
        private long sequence = instanceCount.getAndIncrement();
        private BuilderReference ref;
        private AtomicReference<State> state = new AtomicReference<State>(State.Idle);        
        private int priority;
        
        // Set to true if the reference has had build() called even
        // once.
        private AtomicBoolean built = new AtomicBoolean(false);

        private ReentrantLock stateLock = new ReentrantLock(); 

        public PrioritizedRef( BuilderReference ref ) {
            this.ref = ref;
            resetPriority();
        }

        public PrioritizedRef( State state, BuilderReference ref ) {
            this.state.set(state);
            this.ref = ref;
            resetPriority();
        }

        public void markForBuild() {
            if( state.get() == State.Release ) {
                throw new IllegalStateException("Reference already released:" + ref);
            }
            
            // If we are pending or already reprocessing 
            if( state.get() == State.Pending || state.get() == State.Reprocess ) {
                // This is the one case we can "early out"
                // without getting the lock.  The reason this is
                // not a race condition is because markForBuild() is only
                // called from the apply thread.  The only way it could get 
                // to either of those states is from apply() which is also
                // called on this thread.  There is a small race condition
                // where the builder thread may change state from pending to
                // processing right after we check... but that's ok because
                // that means it's doing what we were already going to ask it
                // to do.
                if( log.isTraceEnabled() ) {
                    log.trace("markForBuild() state already set for building:" + state + "  ref:" + ref);
                }                 
                return;
            }
            stateLock.lock();
            try {
                if( log.isTraceEnabled() ) {
                    log.trace("markForBuild() from:" + state.get() + "  ref:" + ref);
                }            
                // At this point we are either done, processing,
                // or already marked for reprocessing.  We are the 
                // only method that would set pending and markForBuild()
                // is always called from the apply thread so there 
                // won't be a race there.
                switch( state.get() ) {
                    case Done:
                        // We are already in the done pile so it's
                        // good enough just to mark for reprocess and let 
                        // apply relaunch us.
                        state.set(State.Reprocess);
                        break;
                    case Processing:
                        // Just set the state and let the normal apply
                        // pass set to pending.  When we are done processing
                        // then this reference will be put in the done pile.
                        // There is no race because we've locked the state lock
                        // and so will the markDone() method.
                        state.set(State.Reprocess);
                        break;
                    case Idle:
                        // The ref is not in the done pile and not being processed
                        // so we can just execute it and mark pending
                        state.set(State.Pending);
                        execute(this);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected state in markForBuild():" + state);
                }
                if( log.isTraceEnabled() ) {
                    log.trace("to state:" + state.get() + "  ref:" + ref);
                }
            } finally {
                stateLock.unlock();
            }
        }
        
        public void markForRelease() {
            if( state.get() == State.Release ) {
                throw new IllegalStateException("Reference already released:" + ref);
            }
            
            stateLock.lock();
            try {
                if( log.isTraceEnabled() ) {
                    log.trace("markForRelease() from:" + state.get() + "  ref:" + ref);
                }            
                switch( state.get() ) {
                    case Pending:
                        // Cancel the task.  It is possible that the task cannot
                        // be canceled because it has already begun executing and
                        // is now waiting for the lock we are holding.  So we always
                        // must mark it for release and let the run() method
                        // put it in the done pile.
                        state.set(State.Release);
                        if( cancel(this) ) {
                            // However, if we DID cancel it then there is nothing around
                            // to put this in the done pile... so we must
                            done.put(this);
                        }
                        break;
                    case Processing:
                        // All we can do is mark the state to release and
                        // let the apply() pass fix it up.
                        state.set(State.Release);
                        break;
                    case Reprocess:
                    case Done:
                        // We are already waiting in the done pile so we only
                        // need to change state
                        state.set(State.Release);
                        break; 
                    case Idle:
                        // We need to be put in the done pile and marked for
                        // release
                        state.set(State.Release);
                        done.put(this);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected state in markForRelease():" + state);
                }
                if( log.isTraceEnabled() ) {
                    log.trace("to state:" + state.get() + "  ref:" + ref);
                }                
            } finally {
                stateLock.unlock();
            }           
        }

        public void apply() {
            
            // There is no way to 'early out' here because in call cases
            // we may be racing with the builder thread for 'mark done' state.
            // By the time apply() is called, this reference has already been
            // removed from the done pile.
            
            stateLock.lock();
            try {
                if( log.isTraceEnabled() ) {
                    log.trace("apply() from:" + state.get() + "  ref:" + ref);
                }            
                switch( state.get() ) {
                    case Done:
                        // The classic
                        ref.apply(Builder.this);
                        
                        // We're just hanging around now
                        state.set(State.Idle);
                        break;
                    case Release:
                        // Release the reference and remove our tracking
                        refMap.remove(ref);
                        if( built.get() ) {
                            // Only release if it has been built at least once
                            ref.release(Builder.this);
                        } else {
                            if( log.isTraceEnabled() ) {
                                log.trace("Released object was never built:" + ref);
                            }
                        }
                        break;
                    case Reprocess:
                        // Always apply before rebuilding
                        ref.apply(Builder.this);
                        state.set(State.Idle);
                        markForBuild();
                        break;
                    case Pending:
                    case Processing:
                    case Idle:
                    default:
                        throw new IllegalStateException("Unexpected state in apply():" + state + ", ref:" + ref);
                }
                if( log.isTraceEnabled() ) {
                    log.trace("to state:" + state.get() + "  ref:" + ref);
                }                
            } finally {
                stateLock.unlock();
            }
        }

        protected void markProcessing() {
        
            // This is called from the builder thread.  Our state
            // should be pending but there could have been calls from
            // the apply() thread while we were queuing up that changed
            // that state.
            
            stateLock.lock();
            try {
                if( log.isTraceEnabled() ) {
                    log.trace("markProcessing() from:" + state.get() + "  ref:" + ref);
                }            
                switch( state.get() ) {
                    case Pending:
                        // Perfect.
                        state.set(State.Processing);
                        break;
                    case Processing:
                        throw new IllegalStateException("Double-processing detected for:" + ref);
                    case Done:
                        // The builder threads are the only thing that would have
                        // set 'done' state so somehow processing overlapped... which is
                        // a bug check
                        throw new IllegalStateException("Process-overlapping done detected for:" + ref);
                    case Release:
                        // This reference was marked for release after we were
                        // queued but before our lock.  We should avoid processing
                        // it but we still need to add ourselves to the done pile.
                        markDone();
                        return;
                    case Reprocess:
                        // This shouldn't happen in markProcessing because a Pending
                        // reference would never have made it to the state change.
                    case Idle:
                        // Nor should this because the only transition to Idle is
                        // from done states.
                    default:
                        throw new IllegalStateException("Unexpected state in apply():" + state + ", ref:" + ref);
                }                                                
                // Track that has been built at least once
                built.set(true);
                if( log.isTraceEnabled() ) {
                    log.trace("to state:" + state.get() + "  ref:" + ref);
                }                
            } finally {
                stateLock.unlock();
            }
        }
        
        protected void markDone() {
            
            // This is called from the builder thread when processing
            // has completed.  The state may have been changed while we
            // were processing this reference
            
            stateLock.lock();
            try {
                if( log.isTraceEnabled() ) {
                    log.trace("markDone() from:" + state.get() + "  ref:" + ref);
                }            
                switch( state.get() ) {
                    case Pending:
                        // There is no case where we could possible get to pending
                        // from processing or any of its follow-on states.
                        // Only Idle goes to Pending and only Done goes to Idle
                        // and we are the only place that marks done.
                        throw new IllegalStateException("Unexpected state in markDone():" + state + ", ref:" + ref);
                    case Processing:
                        // The normal state change.  We need to mark ourselves done
                        // and add ourselves to the done pile
                        state.set(State.Done);
                        done.put(this);
                        break;
                    case Done:
                        // The builder threads are the only thing that would have
                        // set 'done' state so somehow processing overlapped... which is
                        // a bug check
                        throw new IllegalStateException("Process-overlapping done detected for:" + ref);
                    case Release:
                        // Ok, we were marked for release while we were processing.
                        // Just put this in the 'done' pile.
                        done.put(this);
                        break;
                    case Reprocess: 
                        // We were marked for reprocessing while we were processing.
                        // This is also ok
                        done.put(this);
                        break;
                    case Idle:
                        // No way we can get to Idle without going through Done first.
                        // Only builder threads set done state so that means another
                        // thread processed and set done around us and the apply() was even
                        // run.  So many state checks had to fail.
                    default:
                        throw new IllegalStateException("Unexpected state in apply():" + state + ", ref:" + ref);
                }
                if( log.isTraceEnabled() ) {
                    log.trace("to state:" + state.get() + "  ref:" + ref);
                }                
            } finally {
                stateLock.unlock();
            }
        }

        public final void resetPriority() {
            this.priority = ref.getPriority();
        }

        @Override
        public int compareTo( PrioritizedRef pr ) {
            int diff = priority - pr.priority;
            if( diff == 0 )
                diff = (int)(sequence - pr.sequence);
            return diff;
        }

        @Override
        public void run() {
 
            markProcessing();
            try {
                ref.build();
            } catch( Exception e ) {
                handleError(e);                
            } finally {            
                markDone();                      
            } 
        }
    }
    
    private class BuilderThreadFactory implements ThreadFactory {
        
        @Override
        public Thread newThread( Runnable r ) {
            Thread result = Executors.defaultThreadFactory().newThread(r);
            
            String s = result.getName();
            result.setName(name + "[" + s + "]");
            result.setDaemon(true);
            
            log.info("Created thread:" + result);
                        
            return result;            
        }
    } 
}