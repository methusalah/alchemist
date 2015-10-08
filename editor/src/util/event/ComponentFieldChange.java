package util.event;

import com.simsilica.es.EntityComponent;

public class ComponentFieldChange extends Event {
	public final EntityComponent comp;
	public final String fieldName;
	public final Object newValue;
	
	public ComponentFieldChange(EntityComponent comp,
			String fieldName,
			Object newValue) {
		this.comp = comp;
		this.fieldName = fieldName;
		this.newValue = newValue;
	}
}
