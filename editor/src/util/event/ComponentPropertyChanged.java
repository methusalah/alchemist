package util.event;

import com.simsilica.es.EntityComponent;

public class ComponentPropertyChanged extends Event {
	public final EntityComponent comp;
	public final String propertyName;
	public final Object newValue;
	
	public ComponentPropertyChanged(EntityComponent comp,
			String fieldName,
			Object newValue) {
		this.comp = comp;
		this.propertyName = fieldName;
		this.newValue = newValue;
	}
}
