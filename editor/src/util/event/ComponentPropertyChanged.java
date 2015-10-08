package util.event;

import com.simsilica.es.EntityComponent;

public class ComponentPropertyChanged extends Event {
	public final EntityComponent comp;
	public final String fieldName;
	public final Object newValue;
	
	public ComponentPropertyChanged(EntityComponent comp,
			String fieldName,
			Object newValue) {
		this.comp = comp;
		this.fieldName = fieldName;
		this.newValue = newValue;
	}
}
