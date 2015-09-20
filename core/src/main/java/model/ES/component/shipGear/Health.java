package model.ES.component.shipGear;

import com.simsilica.es.EntityComponent;

public class Health implements EntityComponent{
	private final int max, value;
	
	public Health(int max, int value) {
		this.max = max;
		this.value = value;
	}

	public int getMax() {
		return max;
	}

	public int getValue() {
		return value;
	}
}
