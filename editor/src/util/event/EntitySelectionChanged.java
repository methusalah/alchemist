package util.event;

import presenter.EntityNode;

import com.simsilica.es.EntityId;

public class EntitySelectionChanged extends Event {
	private final EntityNode ep;
	private final EntityId eid;
	
	public EntitySelectionChanged(EntityNode ep) {
		this.ep = ep;
		this.eid = null;
	}

	public EntitySelectionChanged(EntityId eid) {
		this.ep = null;
		this.eid = eid;
	}

	public EntityNode getEntityPresenter() {
		return ep;
	}

	public EntityId getEntityId() {
		return eid;
	}
	
}
