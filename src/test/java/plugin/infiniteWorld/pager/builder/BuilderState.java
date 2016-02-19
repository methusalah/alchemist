package plugin.infiniteWorld.pager.builder;
/*
 * ${Id}
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

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

import util.LogUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Manages a Builder instance making sure applyUpdates() is
 *  called once per frame with a defined max value.  This state
 *  also does some builder-related cleanup processing in cleanup()
 *  by processing any pending updates before returning.
 *
 *  @author    Paul Speed
 */
public class BuilderState extends AbstractAppState {

    static Logger log = LoggerFactory.getLogger(BuilderState.class);
    
    private Builder builder;
    private int maxUpdates; 
    
    public BuilderState( int poolSize, int maxUpdates ) {
        this("Builder", poolSize, maxUpdates);
    }

    public BuilderState( String name, int poolSize, int maxUpdates ) {
        this.builder = new Builder(name, poolSize);
        this.maxUpdates = maxUpdates;
    }

    /**
     *  Sets the maximum number of updates that will be applied
     *  in a single frame.  Keep this value low to avoid frame drops
     *  but keep it high enough that updates get applied in a timely
     *  manner.  The proper value is application specific and will
     *  depend on how much time it takes to apply updates versus how
     *  fast updates come in.
     */
    public void setMaxUpdates( int i ) {
        this.maxUpdates = i;
    }
    
    public int getMaxUpdates() {
        return maxUpdates;
    }

    /**
     *  Returns the managed Builder object for queing BuilderReferences to be 
     *  built by the background thread pool.
     */
    public Builder getBuilder() {
        return builder;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
    	// TODO Auto-generated method stub
    	super.initialize(stateManager, app);
    }

    @Override
    public void cleanup() {
        log.trace("cleanup()");    
        builder.shutdown();
        
        // And let's apply all updates while we are here
        // I'll do them in batches of 100 to be safe
        int updates;
        while( (updates = builder.applyUpdates(100)) > 0 ) {
            log.trace("Builder cleanup: applied " + updates + " updates.");   
        }
        log.trace("Builder cleanup: applied " + updates + " updates.");   
        
        // Can't restart it once shutdown so we might as well
        // poison the well
        builder = null;
    }


    @Override
    public void setEnabled(boolean enabled) {
    	super.setEnabled(enabled);
    	if(enabled)
    		onEnable();
    	else
    		onDisable();
    }
    
    protected void onEnable() {
    	LogUtil.info("onEnabled");
        // We have to check because the first time through
        // it won't be paused.
        if( builder.isPaused() ) {
            builder.resume();
        }
    }

    @Override
    public void update( float tpf ) {       
        builder.applyUpdates(maxUpdates);
    }

    protected void onDisable() {
        builder.pause();
    }
}