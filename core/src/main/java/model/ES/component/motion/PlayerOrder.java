package model.ES.component.motion;

import util.entity.Comp;
import util.geometry.geom2d.Point2D;

public class PlayerOrder implements Comp {
	private final boolean thrust;
	private final boolean fire;
	private final Point2D target;
	
	public PlayerOrder(boolean thrust, boolean fire, Point2D target) {
		this.thrust = thrust;
		this.fire = fire;
		this.target = target;
	}

	public boolean isThrust() {
		return thrust;
	}

	public boolean isFire() {
		return fire;
	}

	public Point2D getTarget() {
		return target;
	}
	
	
	
}
