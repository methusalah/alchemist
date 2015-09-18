package model.ES.processor.physic.collision;

import model.ES.component.ToRemove;
import model.ES.component.physic.collision.DestroyedOnTouch;
import model.ES.component.physic.collision.Touching;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class TouchingDestructionProc extends Processor {

	@Override
	protected void registerSets() {
		register(DestroyedOnTouch.class, Touching.class);

	}

	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
		manage(e, elapsedTime);
	}
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		manage(e, elapsedTime);
	}

	private void manage(Entity e, float elapsedTime) {
		setComp(e, new ToRemove());
	}

}
