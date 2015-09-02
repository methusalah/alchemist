package model.ES.component;

import com.simsilica.es.EntityComponent;

public class LifeTime implements EntityComponent{
	private final double lifeTime;
	private final long startTime;
	
	public LifeTime(double lifeTime) {
		this.lifeTime = lifeTime;
		this.startTime = System.currentTimeMillis();
	}
	
	public LifeTime getUpdated(){
		double newlife = lifeTime-System.currentTimeMillis()-startTime;
		return new LifeTime(newlife);
	}
	
	public boolean isStillAlive(){
		return lifeTime > 0;
	}
}
