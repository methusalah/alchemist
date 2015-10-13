package model.ES.component.relation;

import util.geometry.geom3d.Point3D;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class PlanarHolding implements EntityComponent {
	public final Point3D localPosition;
	public final double localOrientation;
	
	public PlanarHolding(@JsonProperty("localPosition")Point3D localPosition,
			@JsonProperty("localOrientation")double localOrientation) {
		this.localPosition = localPosition;
		this.localOrientation = localOrientation;
	}

	public Point3D getLocalPosition() {
		return localPosition;
	}

	public double getLocalOrientation() {
		return localOrientation;
	}
}
