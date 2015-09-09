package model.ES.component;

import com.simsilica.es.EntityComponent;

public class Cooldown implements EntityComponent{
	private final long start;
	private final double duration;
	
	public Cooldown(long start, double duration) {
		this.start = start;
		this.duration = duration;
	}

	public long getStart() {
		return start;
	}

	public double getDuration() {
		return duration;
	}
}
