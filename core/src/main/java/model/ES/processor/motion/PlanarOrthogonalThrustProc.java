package model.ES.processor.motion;

import model.ES.component.planarMotion.PlanarStance;
import model.ES.component.planarMotion.PlanarThrust;
import model.ES.component.planarMotion.PlanarWippingInertia;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class PlanarOrthogonalThrustProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(PlanarThrust.class, PlanarStance.class, PlanarWippingInertia.class);
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
		PlanarThrust thrust = e.get(PlanarThrust.class);
		PlanarWippingInertia inertia = e.get(PlanarWippingInertia.class);
		
		setComp(e, new PlanarWippingInertia(inertia.getVelocity(), thrust.getDirection().getRotation(AngleUtil.RIGHT).getScaled(elapsedTime)));
		removeComp(e, PlanarThrust.class);
		
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + System.lineSeparator());
		app.getDebugger().add(sb.toString());
	}
}
