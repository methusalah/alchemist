package com.brainless.alchemist.model.ECS.pipeline;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;

import com.brainless.alchemist.model.state.DataState;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;

/**
 * Processors are the core logic of the entity/component/system architecture. In the literature, it is often called "System".
 * This class provide an implementation of the Processor interface to help the user creating standardized, homogeneous
 * and readable code with the minimum effort.
 * 
 * As an abstract class, it is intended to by extended into many specific and grained piece of logic. Each Processor needs to
 * subscribe to one or more entity set, then can manage the events occurring on these sets.
 * 
 * Processors are included into pipelines and plugged to the main application to be ran at each tick.
 * 
 * @author Benoît
 *
 */
public abstract class BaseProcessor extends Processor {
	protected EntityData entityData;
	private final Map<String, EntitySet> sets = new HashMap<>();
	
	/**
	 * Called by AppStateManager. Should not be used.
	 */
	@Override
	public final void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
        entityData = stateManager.getState(DataState.class).getEntityData();

        registerSets();
        for(EntitySet set : sets.values())
	        for (Entity e : set)
	            onEntityAdded(e);
        onInitialized(stateManager);
	}
	
	/**
	 * Called by AppStateManager. Should not be used.
	 */
    @Override
    public final void update(float elapsedTime) {
    	if(!isEnabled())
    		return;
    	try{
	        for(EntitySet set : sets.values()){
		        if (set.applyChanges()) {
		            for (Entity e : set.getChangedEntities()) {
		            	onEntityUpdated(e);
		            }
		            for (Entity e : set.getAddedEntities()) {
		            	onEntityAdded(e);
		            }
		            for (Entity e : set.getRemovedEntities()) {
		            	onEntityRemoved(e);
		            }
		        }
	            for (Entity e : set) {
	            	onEntityEachTick(e);
	            }
	        }
	        onUpdated();
    	} catch(RuntimeException e){
    		LoggerFactory.getLogger(getClass()).error("Exception in processor : " + this.getClass().getSimpleName() + " : " + e.getMessage());
    		e.printStackTrace();
    		
    	}
    }

	/**
	 * Called by AppStateManager. Should not be used.
	 */
    @Override
    public final void cleanup() {
        for(EntitySet set : sets.values())
        	for (Entity e : set)
        		onEntityRemoved(e);
        onCleanup();
        for(EntitySet set : sets.values())
        	set.release();
        super.cleanup();
    }

    /**
     * Creates a default entity set with the given component classes.
     * 
     * @param compClass
     */
    @SafeVarargs
	protected final void registerDefault(Class<? extends EntityComponent>... compClass){
    	sets.put("", entityData.getEntities(compClass));
    }

    /**
     * Creates a specific and nammed entity set with the given component classes.
     * Used to work with many entity sets.
     * @param compClass
     */
    @SafeVarargs
	protected final void register(String setName, Class<? extends EntityComponent>... compClass){
    	sets.put(setName, entityData.getEntities(compClass));
    }
    
    /**
     * Return the default entity set, or null if it hasn't been registered.
     * @return
     */
    protected EntitySet getDefaultSet(){
    	return sets.get("");
    }
    
    /**
     * Return the specific entity set found by its name, or null if it hasn't been registered.
     * @return
     */
    protected EntitySet getSet(String setName){
    	return sets.get(setName);
    }

    /**
     * Convenient method to quickly assign a component to an entity from a registered set.
     * @return
     */
    protected final void setComp(Entity e, EntityComponent comp){
		entityData.setComponent(e.getId(), comp);
    }

    /**
     * Convenient method to quickly remove a component from entity from a registered set.
     * @return
     */
    protected final <T extends EntityComponent> void removeComp(Entity e, Class<T> compClass){
		entityData.removeComponent(e.getId(), compClass);
    }
    
    /**
     * Each Processor must override this method and declare to which set of component class it will react.
     * 
     * It is allowed to register to nothing, and consider the processor as a basic app state.
     *
     * @return entity used with this system.
     */
    protected abstract void registerSets();

    /**
     * User code called after initialization, to complete the processor.
     * Called by the state manager.
     * The state manager to which the processor is attached is given in parameter, to allow communication between app state.
     * Note : initialization occurred at the end of the state manager initialization. It's best practice to attach all your
     * processors at the same moment, to have them accessible at this method call. 
     * @param stateManager
     */
    protected void onInitialized(AppStateManager stateManager){}
    
    /**
     * Called each frame.
     *
     * @param tpf
     */
    protected void onUpdated(){}

    /**
     * Called when an entity is added to one of the registered set.
     *
     * @param e entity to add.
     */
    protected void onEntityAdded(Entity e){}

    /**
     * Called when an entity got an update in one of the registered set.
     *
     * @param e updated entity.
     */
    protected void onEntityUpdated(Entity e){}

    /**
     * Called when an entity is removed from one of the registered set.
     *
     * @param e removed entity.
     */
    protected void onEntityRemoved(Entity e){}

    /**
     * Called each tick for each entity in the registered sets.
     *
     * @param e removed entity.
     */
    protected void onEntityEachTick(Entity e){}

    /**
     * Called when the system is removed, used to clean his mess.
     */
    protected void onCleanup(){}
}
