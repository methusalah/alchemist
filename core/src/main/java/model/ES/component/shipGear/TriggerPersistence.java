package model.ES.component.shipGear;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class TriggerPersistence implements EntityComponent {
	public final int duration;
	public final int range;
	
	public TriggerPersistence(@JsonProperty("duration")int duration, @JsonProperty("range")int range) {
		this.duration = duration;
		this.range = range;
	}
}
