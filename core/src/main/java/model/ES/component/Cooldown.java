package model.ES.component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class Cooldown implements EntityComponent{
	public final long start;
	public final double duration;
	
	public Cooldown(@JsonProperty("start")long start, @JsonProperty("duration")double duration) {
		this.start = start;
		this.duration = duration;
	}
}
