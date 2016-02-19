package processor.logic.motion.physic.collisionDetection;

import java.util.List;

import com.simsilica.es.Entity;

import component.motion.PlanarStance;
import component.motion.physic.CircleCollisionShape;
import component.motion.physic.EdgedCollisionShape;
import component.motion.physic.Physic;
import model.ECS.pipeline.Processor;
import util.geometry.geom2d.Circle2D;
import util.geometry.geom2d.Point2D;
import util.geometry.geom2d.Segment2D;
import util.math.AngleUtil;

public class CircleEdgeCollisionProc extends Processor {
	
	@Override
	protected void registerSets() {
		register("edge", Physic.class, PlanarStance.class, EdgedCollisionShape.class);
		register("circle", Physic.class, PlanarStance.class, CircleCollisionShape.class);
	}
	
	@Override
	protected void onUpdated() {
    	for(Entity edgeEntity : getSet("edge"))
        	for(Entity circleEntity : getSet("circle"))
        		recordCollisionState(edgeEntity, circleEntity);
	}

	private void recordCollisionState(Entity edged, Entity circled) {
		PlanarStance stance1 = edged.get(PlanarStance.class);
		PlanarStance stance2 = circled.get(PlanarStance.class);
		
		Physic ph1 = edged.get(Physic.class);
		Physic ph2 = circled.get(Physic.class);
		if(ph1.getExceptions().contains(ph2.getType()) || ph2.getExceptions().contains(ph1.getType()))
			return;

		EdgedCollisionShape edgeShape = edged.get(EdgedCollisionShape.class);
		Circle2D circle = new Circle2D(stance2.coord, circled.get(CircleCollisionShape.class).getRadius());

		Point2D impactCoord = null, impactNormal = null;
		double penetration = 0;
		for(Segment2D s : edgeShape.getEdges()){
			List<Point2D> intersections = s.getIntersection(circle).getAll();
			if(!intersections.isEmpty()){
				impactCoord = intersections.get(0);
				if(AngleUtil.getTurn(s.getStart(), s.getEnd(), circle.center) == AngleUtil.CLOCKWISE)
					impactNormal = Point2D.ORIGIN.getTranslation(s.getAngle() - AngleUtil.RIGHT, 1);
				else
					impactNormal = Point2D.ORIGIN.getTranslation(s.getAngle() + AngleUtil.RIGHT, 1);
				penetration = Math.min(impactCoord.getDistance(s.getStart()), impactCoord.getDistance(s.getEnd()));
				break;
			}
		}
		if(penetration != 0)
			CollisionResolutionProc.createCollisionBetween(entityData, edged, circled, penetration, impactNormal, impactCoord);
	}
}


































