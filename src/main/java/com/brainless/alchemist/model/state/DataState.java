package com.brainless.alchemist.model.state;

import com.brainless.alchemist.model.ECS.pipeline.Processor;
import com.simsilica.es.EntityData;

/**
 * The EntityData of the game is stored in this AppState to allow
 * other AppStates to retrieve it.
 *
 * @author Eike Foede, roah
 */
public class DataState extends Processor {
	private final EntityData entityData;
	
    public DataState(EntityData entityData) {
        this.entityData = entityData;
    }
    
    public EntityData getEntityData() {
        return entityData;
    }

	@Override
	protected void registerSets() {
	}
}