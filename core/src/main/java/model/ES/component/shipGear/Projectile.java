package model.ES.component.shipGear;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

import util.geometry.geom2d.Point2D;

public class Projectile implements EntityComponent {
	public final EntityId sender;
	public final Point2D spawningCoord;
	
	public Projectile(@JsonProperty("sender")EntityId sender, @JsonProperty("spawningCoord")Point2D spawningCoord) {
		this.sender = sender;
		this.spawningCoord = spawningCoord;
	}
}
