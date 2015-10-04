package model.ES.component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class LifeTime implements EntityComponent {
	public final long lifeStart;
	public final double duration;
	
	public LifeTime(@JsonProperty("lifeStart")long lifeStart, @JsonProperty("duration")double duration) {
		this.lifeStart = lifeStart;
		this.duration = duration;
	}

}
