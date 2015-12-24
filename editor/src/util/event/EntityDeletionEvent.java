package util.event;

import presenter.EntityNode;

public class EntityDeletionEvent extends Event {
	private final EntityNode ep;
	
	public EntityDeletionEvent(EntityNode ep) {
		this.ep = ep;
	}

	public EntityNode getEp() {
		return ep;
	}
	

}
