package util.event;

import com.simsilica.es.EntityComponent;

public class RemoveComponentEvent extends Event {
	public final Class<? extends EntityComponent> compClass;
	
	public RemoveComponentEvent(Class<? extends EntityComponent> compClass) {
		this.compClass = compClass;
	}
}
