package controller.ECS;

import com.jme3.app.state.AbstractAppState;
import com.simsilica.es.EntityData;
import com.simsilica.es.base.DefaultEntityData;

import model.world.WorldData;

/**
 * The EntityData of the game is stored in this AppState to allow
 * other AppStates to retrieve it.
 *
 * @author Eike Foede, roah
 */
public class DataAppState extends AbstractAppState {
	EntityData entityData;
	WorldData world;
	
    public DataAppState() {
    	entityData = new DefaultEntityData();
    }

    public DataAppState(EntityData entityData, WorldData world) {
        this.entityData = entityData;
        this.world = world;
    }
    
    public EntityData getEntityData() {
        return entityData;
    }

    public WorldData getWorldData(){
    	return world;
    }
    
}