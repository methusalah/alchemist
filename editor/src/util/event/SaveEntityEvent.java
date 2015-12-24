package util.event;

import presenter.EntityNode;


public class SaveEntityEvent extends Event {
	public final EntityNode ep;
	
	public SaveEntityEvent(EntityNode ep) {
		this.ep = ep;
	}
}
