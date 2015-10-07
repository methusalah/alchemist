package util.event;

import com.simsilica.es.EntityComponent;

public class ComponentFieldChange extends Event {
	public final EntityComponent comp;
	public final String fieldName;
	public final Object newValue;
	public final Class<?> type;
	
	public ComponentFieldChange(EntityComponent comp,
			String fieldName,
			Object newValue,
			Class<?> type) {
		this.comp = comp;
		this.fieldName = fieldName;
		this.newValue = newValue;
		this.type = type;
	}
}
