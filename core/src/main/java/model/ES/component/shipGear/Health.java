package model.ES.component.shipGear;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class Health implements EntityComponent{
	public final int max, value;
	
	public Health(@JsonProperty("max")int max, @JsonProperty("value")int value) {
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
