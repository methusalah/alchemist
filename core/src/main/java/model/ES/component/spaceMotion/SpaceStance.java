package model.ES.component.spaceMotion;

import com.simsilica.es.EntityComponent;

import util.geometry.geom3d.Point3D;

public class SpaceStance implements EntityComponent{
	private final Point3D position;
	private final Point3D direction;
	
	public SpaceStance(Point3D position, Point3D direction) {
		this.position = position;
		this.direction = direction;
	}

	public Point3D getPosition() {
		return position;
	}

	public Point3D getDirection() {
		return direction;
	}
}
