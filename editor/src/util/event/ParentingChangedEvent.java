package util.event;

import presenter.EntityNode;


public class ParentingChangedEvent extends Event {
	public final EntityNode child;
	public final EntityNode newParent;
	
	public ParentingChangedEvent(EntityNode child, EntityNode newParent) {
		this.child = child;
		this.newParent = newParent;
	}
}
