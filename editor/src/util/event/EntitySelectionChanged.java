package util.event;

import com.simsilica.es.EntityId;

import model.EntityPresenter;

public class EntitySelectionChanged extends Event {
	private final EntityPresenter ep;
	private final EntityId eid;
	
	public EntitySelectionChanged(EntityPresenter ep) {
		this.ep = ep;
		this.eid = null;
	}

	public EntitySelectionChanged(EntityId eid) {
		this.ep = null;
		this.eid = eid;
	}

	public EntityPresenter getEntityPresenter() {
		return ep;
	}

	public EntityId getEntityId() {
		return eid;
	}
	
}
