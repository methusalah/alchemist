package util.event;

import com.simsilica.es.EntityId;


public class ParentingChangedEvent extends Event {
	public final EntityId child;
	public final EntityId newParent;
	
	public ParentingChangedEvent(EntityId child, EntityId newParent) {
		this.child = child;
		this.newParent = newParent;
	}
}
