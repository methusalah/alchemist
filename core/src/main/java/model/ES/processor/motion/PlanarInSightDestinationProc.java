package model.ES.processor.motion;

import model.ES.component.motion.PlanarInSightDestination;
import model.ES.component.motion.PlanarInertia;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.PlanarThrust;
import util.geometry.geom2d.Point2D;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class PlanarInSightDestinationProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(PlanarInSightDestination.class, PlanarStance.class, PlanarInertia.class);
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
		PlanarStance stance = e.get(PlanarStance.class);
		PlanarInSightDestination dest = e.get(PlanarInSightDestination.class);
		PlanarInertia inertia = e.get(PlanarInertia.class);
		
		
		
		
		setComp(e, new PlanarInertia(inertia.getVelocity(), Point2D.ORIGIN.getTranslation(stance.getOrientation(), 0.001*elapsedTime)));
		removeComp(e, PlanarThrust.class);
		
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + System.lineSeparator());
		app.getDebugger().add(sb.toString());
	}
}
