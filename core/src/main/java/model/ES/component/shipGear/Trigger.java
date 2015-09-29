package model.ES.component.shipGear;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

import util.LogUtil;

public class Trigger implements EntityComponent {
	public final EntityId source;
	public final boolean isToggle;
	
	public Trigger(EntityId source, boolean isToggle) {
		this.source = source;
		this.isToggle = isToggle;
	}
}
