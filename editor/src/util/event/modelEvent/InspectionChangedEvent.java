package util.event.modelEvent;

import model.EntityPresenter;
import util.event.Event;

public class InspectionChangedEvent extends Event {
	private final EntityPresenter entityPresenter;
	
	public InspectionChangedEvent(EntityPresenter entityPresenter) {
		this.entityPresenter = entityPresenter;
	}

	public EntityPresenter getEntityPresenter() {
		return entityPresenter;
	}
	
	
}
