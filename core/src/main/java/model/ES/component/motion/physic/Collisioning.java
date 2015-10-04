package model.ES.component.motion.physic;

import util.geometry.geom2d.Point2D;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class Collisioning implements EntityComponent {
	public final EntityId a, b;
	public final double penetration;
	public final Point2D normal;
	
	public Collisioning(@JsonProperty("a")EntityId a,
			@JsonProperty("b")EntityId b,
			@JsonProperty("penetration")double penetration,
			@JsonProperty("normal")Point2D normal) {
		this.a = a;
		this.b = b;
		this.penetration = penetration;
		this.normal = normal;
	}
}
