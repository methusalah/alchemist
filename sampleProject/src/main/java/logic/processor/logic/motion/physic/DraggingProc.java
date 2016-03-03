package logic.processor.logic.motion.physic;

import org.dyn4j.dynamics.Body;

import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.simsilica.es.Entity;

import component.motion.PlanarVelocityToApply;
import component.motion.physic.Dragging;
import component.motion.physic.Physic;
import logic.processor.Pool;
import logic.util.Point2DAdapter;
import logic.util.Vector2Adapter;
import util.geometry.geom2d.Point2D;
import util.math.PrecisionUtil;

public class DraggingProc extends BaseProcessor {
	@Override
	protected void registerSets() {
		registerDefault(Dragging.class, Physic.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		Physic ph = e.get(Physic.class);
		Dragging dragging = e.get(Dragging.class);
		
		Body b = Pool.bodies.get(e.getId());
		
		
		Point2D actualVelocity = new Point2DAdapter(b.getLinearVelocity());

		double speed = actualVelocity.getLength();
		if(speed > PrecisionUtil.APPROX){
			double dragStrength = speed*speed * dragging.dragging;
			Point2D dragForce = actualVelocity.getNegation().getScaled(dragStrength).getTruncation(speed*ph.getMass());
			b.applyForce(new Vector2Adapter(dragForce));
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
