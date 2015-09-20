package model.ES.processor.interaction;

import model.ES.component.ToRemove;
import model.ES.component.interaction.DestroyedOnTouch;
import model.ES.component.interaction.Touching;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class DestroyedOnTouchProc extends Processor {

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
