package model.ES.processor.motion.physic;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;
import model.ES.component.motion.PlanarVelocityToApply;
import model.ES.component.motion.physic.Dragging;
import model.ES.component.motion.physic.Physic;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.math.PrecisionUtil;

public class DraggingProc extends Processor {
	@Override
	protected void registerSets() {
		register(Dragging.class, Physic.class, PlanarVelocityToApply.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
		manage(e, elapsedTime);
	}

	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime){
		manage(e, elapsedTime);
	}
	
	private void manage(Entity e, float elapsedTime){
		Physic ph = e.get(Physic.class);
		Dragging dragging = e.get(Dragging.class);
		
		Point2D actualVelocity = ph.velocity;

		double speed = actualVelocity.getLength();
		if(speed < PrecisionUtil.APPROX)
			actualVelocity = Point2D.ORIGIN;
		else {
			double dragForce = speed*speed * dragging.getDragging();
			Point2D dragVelocity = actualVelocity.getNegation().getScaled(dragForce);
			PlanarVelocityToApply v = e.get(PlanarVelocityToApply.class);
			dragVelocity = dragVelocity.getAddition(v.getVector());
			setComp(e, new PlanarVelocityToApply(dragVelocity));
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
