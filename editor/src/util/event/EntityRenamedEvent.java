package util.event;

import com.simsilica.es.EntityId;

public class EntityRenamedEvent extends Event {
	public final EntityId eid;
	public final String newName;
	
	public EntityRenamedEvent(EntityId eid, String newName) {
		this.eid = eid;
		this.newName = newName;
	}
}
