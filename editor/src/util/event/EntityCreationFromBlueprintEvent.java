package util.event;

import model.EntityPresenter;
import model.ES.serial.Blueprint;

public class EntityCreationFromBlueprintEvent extends Event {
	private final Blueprint bp;
	private final EntityPresenter parent;
	
	public EntityCreationFromBlueprintEvent(Blueprint bp, EntityPresenter parent) {
		this.bp = bp;
		this.parent = parent;
	}

	public Blueprint getBp() {
		return bp;
	}

	public EntityPresenter getParent() {
		return parent;
	}
	
}
