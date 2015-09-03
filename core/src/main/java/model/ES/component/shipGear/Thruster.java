package model.ES.component.shipGear;

import util.geometry.geom3d.Point3D;

import com.simsilica.es.EntityComponent;

public class Thruster implements EntityComponent{
	private final Point3D direction;
	private final double activationAngle;
	private final double activation;
	private final boolean onOff;
	
	public Thruster(Point3D direction, double activationAngle, double activation, boolean onOff) {
		this.direction = direction;
		this.activation = activation;
		this.activationAngle = activationAngle;
		this.onOff = onOff;
	}

	public Point3D getDirection() {
		return direction;
	}

	public double getActivationAngle() {
		return activationAngle;
	}

	public double getActivation() {
		return activation;
	}

	public boolean isOnOff() {
		return onOff;
	}
}
