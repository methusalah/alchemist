package model.ES.component.shipGear;

import com.simsilica.es.EntityComponent;

public class CapacityActivation implements EntityComponent{
	private final String flag;
	private final boolean activated;
	
	public CapacityActivation(String flag, boolean activated) {
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
