package controller.ECS;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.simsilica.es.EntityData;
import com.simsilica.es.base.DefaultEntityData;

/**
 * The EntityData of the game is stored in this AppState to allow
 * other AppStates to retrieve it.
 *
 * @author Eike Foede, roah
 */
public class EntityDataAppState extends AbstractAppState {
	EntityData entityData;
	
    public EntityDataAppState() {
    	entityData = new DefaultEntityData();
    }

    public EntityDataAppState(EntityData entityData) {
        this.entityData = entityData;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
    }

    public EntityData getEntityData() {
        return entityData;
    }
    @Override
    public void update(float tpf) {
    }
}