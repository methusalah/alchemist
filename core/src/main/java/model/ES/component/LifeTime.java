package model.ES.component;

import com.simsilica.es.EntityComponent;

public class LifeTime implements EntityComponent {
	private final long lifeStart;
	private final double duration;
	
	public LifeTime(long lifeStart, double duration) {
		this.lifeStart = lifeStart;
		this.duration = duration;
	}

	public long getLifeStart() {
		return lifeStart;
	}

	public double getDuration() {
		return duration;
	}
	
}
