package test.java.processor.logic.motion.physic;

import com.simsilica.es.Entity;

import main.java.model.ECS.pipeline.Processor;
import model.ES.component.motion.PlanarVelocityToApply;
import model.ES.component.motion.physic.Dragging;
import model.ES.component.motion.physic.Physic;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.math.PrecisionUtil;

public class DraggingProc extends Processor {
	@Override
	protected void registerSets() {
		registerDefault(Dragging.class, Physic.class, PlanarVelocityToApply.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		Physic ph = e.get(Physic.class);
		Dragging dragging = e.get(Dragging.class);
		
		Point2D actualVelocity = ph.getVelocity();

		double speed = actualVelocity.getLength();
		if(speed < PrecisionUtil.APPROX)
			actualVelocity = Point2D.ORIGIN;
		else {
			double dragForce = speed*speed * dragging.dragging;
			PlanarVelocityToApply v = e.get(PlanarVelocityToApply.class);
			Point2D dragVelocity = actualVelocity.getNegation().getScaled(dragForce).getTruncation(speed*ph.getMass());

			dragVelocity = dragVelocity.getAddition(v.vector);
			setComp(e, new PlanarVelocityToApply(dragVelocity));
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
