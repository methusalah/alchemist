package model.ES.component.shipGear;

import com.simsilica.es.EntityComponent;

public class CapacityTrigger implements EntityComponent{
	private final String flag;
	private final boolean activated;
	
	public CapacityTrigger(String flag, boolean activated) {
		this.flag = flag;
		this.activated = activated;
	}

	public String getFlag() {
		return flag;
	}

	public boolean isActivated() {
		return activated;
	}
}
