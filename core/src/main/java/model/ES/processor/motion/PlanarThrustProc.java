package model.ES.processor.motion;

import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;
import model.ModelManager;
import model.ES.component.motion.PlanarNeededRotation;
import model.ES.component.motion.PlanarThrust;
import model.ES.component.motion.PlanarInertia;
import model.ES.component.motion.PlanarMotionCapacity;
import model.ES.component.motion.PlanarPosition;
import model.ES.component.motion.PlanarMotion;
import model.ES.component.motion.PlayerOrder;
import model.battlefield.army.motion.Motion;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class PlanarThrustProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(PlanarThrust.class, PlanarPosition.class, PlanarInertia.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime){
		manage(e, elapsedTime);
	}

	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime){
		manage(e, elapsedTime);
	}
	
	private void manage(Entity e, float elapsedTime){
		PlanarPosition position = e.get(PlanarPosition.class);
		PlanarInertia inertia = e.get(PlanarInertia.class);
		
		setComp(e, new PlanarInertia(inertia.getVelocity(), Point2D.ORIGIN.getTranslation(position.getOrientation(), 0.001*elapsedTime)));
		removeComp(e, PlanarThrust.class);
		
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + System.lineSeparator());
		app.getDebugger().add(sb.toString());
	}
}
