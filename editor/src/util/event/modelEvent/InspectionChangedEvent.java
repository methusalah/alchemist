package util.event.modelEvent;

import presenter.EntityNode;
import util.event.Event;

public class InspectionChangedEvent extends Event {
	private final EntityNode entityPresenter;
	
	public InspectionChangedEvent(EntityNode entityPresenter) {
		this.entityPresenter = entityPresenter;
	}

	public EntityNode getEntityPresenter() {
		return entityPresenter;
	}
	
	
}
