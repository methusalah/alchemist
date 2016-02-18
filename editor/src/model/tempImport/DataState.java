package model.tempImport;

import com.jme3.app.state.AbstractAppState;
import com.simsilica.es.EntityData;

/**
 * The EntityData of the game is stored in this AppState to allow
 * other AppStates to retrieve it.
 *
 * @author Eike Foede, roah
 */
public class DataState extends AbstractAppState {
	private final EntityData entityData;
	
    public DataState(EntityData entityData) {
        this.entityData = entityData;
    }
    
    public EntityData getEntityData() {
        return entityData;
    }
}