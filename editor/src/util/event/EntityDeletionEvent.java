package util.event;

import model.EntityPresenter;

public class EntityDeletionEvent extends Event {
	private final EntityPresenter ep;
	
	public EntityDeletionEvent(EntityPresenter ep) {
		this.ep = ep;
	}

	public EntityPresenter getEp() {
		return ep;
	}
	

}
