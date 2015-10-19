package model.ES.component.assets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class Health implements EntityComponent{
	public final int max, value;
	
	public Health() {
		max = 0;
		value = 0;
	}
	
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
