package util.event;

import presenter.EntityNode;
import model.ES.serial.Blueprint;

public class EntityCreationFromBlueprintEvent extends Event {
	private final Blueprint bp;
	private final EntityNode parent;
	
	public EntityCreationFromBlueprintEvent(Blueprint bp, EntityNode parent) {
		this.bp = bp;
		this.parent = parent;
	}

	public Blueprint getBp() {
		return bp;
	}

	public EntityNode getParent() {
		return parent;
	}
	
}
