package util.event;

import model.EntityPresenter;


public class ParentingChangedEvent extends Event {
	public final EntityPresenter child;
	public final EntityPresenter newParent;
	
	public ParentingChangedEvent(EntityPresenter child, EntityPresenter newParent) {
		this.child = child;
		this.newParent = newParent;
	}
}
