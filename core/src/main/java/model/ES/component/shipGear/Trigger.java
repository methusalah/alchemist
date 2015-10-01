package model.ES.component.shipGear;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

import util.LogUtil;

public class Trigger implements EntityComponent {
	public final EntityId source;
	public final String name;
	public final boolean triggered;
	
	public Trigger(EntityId source, String name, boolean triggered) {
		this.source = source;
		this.name = name;
		this.triggered = triggered;
	}
}
