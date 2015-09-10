package model.ES.processor.motion;

import model.ES.component.planarMotion.PlanarStance;
import model.ES.component.planarMotion.PlanarNeededVelocity;
import model.ES.component.planarMotion.PlanarWipping;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class PlanarOrthogonalThrustProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(PlanarNeededVelocity.class, PlanarStance.class, PlanarWipping.class);
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
		PlanarNeededVelocity thrust = e.get(PlanarNeededVelocity.class);
		PlanarWipping inertia = e.get(PlanarWipping.class);
		
		setComp(e, new PlanarWipping(inertia.getVelocity(), thrust.getDirection().getRotation(AngleUtil.RIGHT).getScaled(elapsedTime), inertia.getDragging()));
		removeComp(e, PlanarNeededVelocity.class);
		
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + System.lineSeparator());
		app.getDebugger().add(sb.toString());
	}
}
