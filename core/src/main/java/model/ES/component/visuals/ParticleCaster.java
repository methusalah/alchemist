package model.ES.component.visuals;

import com.simsilica.es.EntityComponent;

import util.geometry.geom3d.Point3D;

public class ParticleCaster implements EntityComponent{
	private final Point3D translation;
	private final Point3D direction;
	private double fanning = 0;
	private double initialSpeed = 1;
	private double perSecond = 50;
	private double duration = 1;

	private final boolean emitting;
	
	public ParticleCaster(Point3D translation, Point3D directionVector, boolean emitting) {
		this.translation = translation;
		this.direction = directionVector;
		this.emitting = emitting;
	}

	public double getFanning() {
		return fanning;
	}

	public double getInitialSpeed() {
		return initialSpeed;
	}

	public Point3D getTranslation() {
		return translation;
	}

	public Point3D getDirection() {
		return direction;
	}

	public boolean isEmitting() {
		return emitting;
	}
	
	public double getDuration() {
		return duration;
	}

	public double getPerSecond() {
		return perSecond;
	}

	
}
