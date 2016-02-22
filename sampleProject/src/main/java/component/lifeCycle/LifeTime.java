package component.lifeCycle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class LifeTime implements EntityComponent {
	public final double duration;
	
	public LifeTime() {
		duration = 0;
	}
	
	public LifeTime(@JsonProperty("duration")double duration) {
		this.duration = duration;
	}

	public double getDuration() {
		return duration;
	}

}
