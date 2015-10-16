package model.ES.processor.interaction;

import model.ES.component.ToRemove;
import model.ES.component.interaction.DestroyedOnTouch;
import model.ES.component.interaction.senses.Touching;

import com.simsilica.es.Entity;

import controller.ECS.Processor;

public class DestroyedOnTouchProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(DestroyedOnTouch.class, Touching.class);

	}

	@Override
	protected void onEntityEachTick(Entity e) {
		setComp(e, new ToRemove());
	}

}
