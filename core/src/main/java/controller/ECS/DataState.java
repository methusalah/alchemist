package controller.ECS;

import com.jme3.app.state.AbstractAppState;
import com.simsilica.es.EntityData;
import com.simsilica.es.base.DefaultEntityData;

import model.Command;
import model.world.WorldData;
import view.drawingProcessors.TerrainDrawer;

/**
 * The EntityData of the game is stored in this AppState to allow
 * other AppStates to retrieve it.
 *
 * @author Eike Foede, roah
 */
public class DataState extends AbstractAppState {
	private final EntityData entityData;
	private final WorldData world;
	private final Command command;
	
    public DataState(EntityData entityData, WorldData world, Command command) {
        this.entityData = entityData;
        this.world = world;
        this.command = command;
    }
    
    public EntityData getEntityData() {
        return entityData;
    }

    public WorldData getWorldData(){
    	return world;
    }
    
    public Command getCommand() {
		return command;
	}
}