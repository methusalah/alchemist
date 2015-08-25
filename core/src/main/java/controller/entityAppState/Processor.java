package controller.entityAppState;

import java.util.ArrayList;
import java.util.List;

import app.CosmoVania;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.simsilica.es.ComponentFilter;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;


public abstract class Processor extends AbstractAppState {
	
	protected EntityData entityData;
	protected List<EntitySet> sets = new ArrayList<>();
	
	protected CosmoVania app;
	
	@Override
	public final void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		this.app = (CosmoVania)app;
        entityData = stateManager.getState(EntityDataAppState.class).getEntityData();

        registerSets();
        for(EntitySet set : sets)
	        for (Entity e : set)
	            onEntityAdded(e, 0.001f);
        onInitialized();
	}
	
    @Override
    public final void update(float elapsedTime) {
        for(EntitySet set : sets)
	        if (set.applyChanges()) {
	            for (Entity e : set.getChangedEntities()) {
	            	onEntityUpdated(e, elapsedTime);
	            }
	            for (Entity e : set.getAddedEntities()) {
	            	onEntityAdded(e, elapsedTime);
	            }
	            for (Entity e : set.getRemovedEntities()) {
	            	onEntityRemoved(e, elapsedTime);
	            }
	        }
        onUpdated(elapsedTime);
    }

    @Override
    public final void cleanup() {
        for(EntitySet set : sets)
        	for (Entity e : set)
        		onEntityRemoved(e, 0.001f);
        onCleanup();
        for(EntitySet set : sets)
        	set.release();
        super.cleanup();
    }

    @SafeVarargs
	protected final void register(Class<? extends EntityComponent>... compClass){
    	sets.add(entityData.getEntities(compClass));
    }

    @SafeVarargs
	protected final void register(ComponentFilter filter, Class<? extends EntityComponent>... compClass){
    	sets.add(entityData.getEntities(filter, compClass));
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

    protected void onInitialized(){}
    
    /**
     * Called each frame.
     *
     * @param tpf
     */
    protected void onUpdated(float elapsedTime){}

    /**
     * Called when an entity is added.
     *
     * @param e entity to add.
     */
    protected void onEntityAdded(Entity e, float elapsedTime){}

    /**
     * Called when an entity got an update.
     *
     * @param e updated entity.
     */
    protected void onEntityUpdated(Entity e, float elapsedTime){}

    /**
     * Called when an entity is removed.
     *
     * @param e removed entity.
     */
    protected void onEntityRemoved(Entity e, float elapsedTime){}

    /**
     * Called when the system is removed, used to clean his mess.
     */
    protected void onCleanup(){}
}
