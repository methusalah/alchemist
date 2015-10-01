package model.ES.component;

import com.simsilica.es.EntityComponent;

public class Cooldown implements EntityComponent{
	public final long start;
	public final double duration;
	
	public Cooldown(long start, double duration) {
		this.start = start;
		this.duration = duration;
	}
}
