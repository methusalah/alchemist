package model.ES.component.motion;

import util.geometry.geom2d.Point2D;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class PlanarVelocityToApply implements EntityComponent {
	public final Point2D vector;
	
	public PlanarVelocityToApply() {
		vector = Point2D.ORIGIN;
	}
	
	public PlanarVelocityToApply(@JsonProperty("vector")Point2D vector) {
		this.vector = vector;
	}

	public Point2D getVector() {
		return vector;
	}
}
