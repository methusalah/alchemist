package main.java.model.ECS.pipeline;

import java.util.HashMap;
import java.util.Map;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import main.java.model.state.DataState;
import util.LogUtil;


public abstract class Processor extends AbstractAppState {
	
	protected EntityData entityData;
	private Map<String, EntitySet> sets = new HashMap<>();
	
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
    		LogUtil.severe("Exception in processor : " + this.getClass().getSimpleName() + " : " + e.getMessage());
    		e.printStackTrace();
    		
    	}
    }

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

    @SafeVarargs
	protected final void registerDefault(Class<? extends EntityComponent>... compClass){
    	sets.put("", entityData.getEntities(compClass));
    }

    @SafeVarargs
	protected final void register(String setName, Class<? extends EntityComponent>... compClass){
    	sets.put(setName, entityData.getEntities(compClass));
    }
    
    protected EntitySet getDefaultSet(){
    	return sets.get("");
    }
    
    protected EntitySet getSet(String setName){
    	return sets.get(setName);
    }

    protected final void setComp(Entity e, EntityComponent comp){
		entityData.setComponent(e.getId(), comp);
    }

    protected final <T extends EntityComponent> void removeComp(Entity e, Class<T> compClass){
		entityData.removeComponent(e.getId(), compClass);
    }
    /**
     * Activate the system.
     *
     * @return entity used with this system.
     */
    protected abstract void registerSets();

    protected void onInitialized(AppStateManager stateManager){}
    
    /**
     * Called each frame.
     *
     * @param tpf
     */
    protected void onUpdated(){}

    /**
     * Called when an entity is added.
     *
     * @param e entity to add.
     */
    protected void onEntityAdded(Entity e){}

    /**
     * Called when an entity got an update.
     *
     * @param e updated entity.
     */
    protected void onEntityUpdated(Entity e){}

    /**
     * Called when an entity is removed.
     *
     * @param e removed entity.
     */
    protected void onEntityRemoved(Entity e){}

    /**
     * Called each frame for each entity.
     *
     * @param e removed entity.
     */
    protected void onEntityEachTick(Entity e){}

    /**
     * Called when the system is removed, used to clean his mess.
     */
    protected void onCleanup(){}
}
