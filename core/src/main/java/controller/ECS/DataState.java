package controller.ECS;

import com.jme3.app.state.AbstractAppState;
import com.simsilica.es.EntityData;

import model.Command;

/**
 * The EntityData of the game is stored in this AppState to allow
 * other AppStates to retrieve it.
 *
 * @author Eike Foede, roah
 */
public class DataState extends AbstractAppState {
	private final EntityData entityData;
	private final Command command;
	
    public DataState(EntityData entityData, Command command) {
        this.entityData = entityData;
        this.command = command;
    }
    
    public EntityData getEntityData() {
        return entityData;
    }
    
    public Command getCommand() {
		return command;
	}
}