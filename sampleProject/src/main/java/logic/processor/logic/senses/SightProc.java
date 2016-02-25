package logic.processor.logic.senses;

import java.util.ArrayList;
import java.util.List;

import com.brainless.alchemist.model.ECS.pipeline.Processor;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import component.ability.Sighting;
import component.motion.PlanarStance;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;

public class SightProc extends Processor {

	@Override
	protected void registerSets() {
		register("watcher", Sighting.class, PlanarStance.class);
		register("watchable", PlanarStance.class);
	}

	@Override
	protected void onUpdated() {
		for(Entity watcher : getSet("watcher")){
			Sighting sighting = watcher.get(Sighting.class);
			List<EntityId> entitiesInSight = new ArrayList<>();
			for(Entity other : getSet("watchable")){
				if(isInSight(watcher, other))
					entitiesInSight.add(other.getId());
			}
			setComp(watcher, new Sighting(sighting.range, sighting.angle, entitiesInSight));
		}
	}

	private boolean isInSight(Entity watcher, Entity other) {
		Sighting sighting = watcher.get(Sighting.class);
		PlanarStance watcherStance = watcher.get(PlanarStance.class);
		
		PlanarStance stance = other.get(PlanarStance.class);
		
		Point2D vector = stance.coord.getSubtraction(watcherStance.coord); 
		if(vector.getLength() < sighting.range)
			if(AngleUtil.getSmallestDifference(vector.getAngle(), watcherStance.orientation.getValue()) < sighting.angle/2)
				return true;
		return false;
	}
}
