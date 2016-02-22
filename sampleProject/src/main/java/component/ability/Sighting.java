package component.ability;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class Sighting implements EntityComponent{
	public final double range, angle;
	public final List<EntityId> entitiesInSight;
	
	public Sighting(){
		this.range = 0;
		this.angle = 0;
		this.entitiesInSight = new ArrayList<>();
	}
	
	public Sighting(@JsonProperty("range")double range,
			@JsonProperty("angle")double angle,
			@JsonProperty("entitiesInSight")List<EntityId> entitiesInSight) {
		this.range = range;
		this.angle = angle;
		this.entitiesInSight = entitiesInSight;
	}

	public double getRange() {
		return range;
	}

	public double getAngle() {
		return angle;
	}

	public List<EntityId> getEntitiesInSight() {
		return entitiesInSight;
	}
	
}
