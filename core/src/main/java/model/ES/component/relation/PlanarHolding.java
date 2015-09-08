package model.ES.component.relation;

import util.geometry.geom3d.Point3D;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class PlanarHolding implements EntityComponent {
	private final EntityId holder;
	private final Point3D localPosition;
	private final double localOrientation;
	
	public PlanarHolding(EntityId holder, Point3D localPosition, double localOrientation) {
		this.holder = holder;
		this.localPosition = localPosition;
		this.localOrientation = localOrientation;
	}

	public EntityId getHolder() {
		return holder;
	}

	public Point3D getLocalPosition() {
		return localPosition;
	}

	public double getLocalOrientation() {
		return localOrientation;
	}
}
