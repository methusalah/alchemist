package model.ES.component.interaction.senses;

import java.util.List;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class Sighting implements EntityComponent{
	public final double range, angle;
	public final List<EntityId> entitiesInSight;
	
	public Sighting(double range, double angle, List<EntityId> entitiesInSight) {
		this.range = range;
		this.angle = angle;
		this.entitiesInSight = entitiesInSight;
	}
	
}
