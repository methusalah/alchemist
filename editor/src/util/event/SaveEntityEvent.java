package util.event;

import com.simsilica.es.EntityId;


public class SaveEntityEvent extends Event {
	public final EntityId eid;
	
	public SaveEntityEvent(EntityId eid) {
		this.eid = eid;
	}
}
