package model.ES.component.camera;

import util.geometry.geom3d.Point3D;

import com.simsilica.es.EntityComponent;

public class LookPoint implements EntityComponent {
	private final Point3D pos;
	
	public LookPoint(Point3D pos) {
		this.pos = pos;
	}

	public Point3D getPos() {
		return pos;
	}

}
