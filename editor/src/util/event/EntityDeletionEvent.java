package util.event;

import com.simsilica.es.EntityId;

public class EntityDeletionEvent extends Event {
	public final EntityId eid;
	
	public EntityDeletionEvent(EntityId eid) {
		this.eid = eid;
	}
}
