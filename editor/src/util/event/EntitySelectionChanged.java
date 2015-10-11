package util.event;

import com.simsilica.es.EntityId;

public class EntitySelectionChanged extends Event {
	public final EntityId eid;
	
	public EntitySelectionChanged(EntityId eid) {
		this.eid = eid;
	}
}
