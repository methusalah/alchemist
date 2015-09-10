package model.ES.processor.motion;

import model.ES.component.planarMotion.PlanarInSightDestination;
import model.ES.component.planarMotion.PlanarStance;
import model.ES.component.planarMotion.PlanarNeededVelocity;
import model.ES.component.planarMotion.PlanarWipping;
import util.geometry.geom2d.Point2D;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class PlanarInSightDestinationProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(PlanarInSightDestination.class, PlanarStance.class, PlanarWipping.class);
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
		PlanarWipping inertia = e.get(PlanarWipping.class);
		
		
		
		
		setComp(e, new PlanarWipping(inertia.getVelocity(), Point2D.ORIGIN.getTranslation(stance.getOrientation(), 0.001*elapsedTime), inertia.getDragging()));
		removeComp(e, PlanarNeededVelocity.class);
		
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + System.lineSeparator());
		app.getDebugger().add(sb.toString());
	}
}
