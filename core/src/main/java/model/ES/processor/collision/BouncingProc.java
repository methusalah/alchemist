package model.ES.processor.collision;

import java.util.ArrayList;

import model.ES.component.collision.BounceOnCollision;
import model.ES.component.collision.Collision;
import model.ES.component.collision.Physic;
import model.ES.component.planarMotion.PlanarWipping;
import util.geometry.geom2d.Point2D;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class BouncingProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(Physic.class, BounceOnCollision.class, PlanarWipping.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		Physic ph = e.get(Physic.class);
		PlanarWipping wipping = e.get(PlanarWipping.class);
		
		if(ph.hasCollision()){
			Point2D bounceVelocity = Point2D.ORIGIN;
			for(Collision c : ph.getCurrentCollisions()){
				bounceVelocity = bounceVelocity.getAddition(c.normal);
			}
			bounceVelocity = bounceVelocity.getScaled(wipping.getVelocity().getLength() *0.5);
			setComp(e, new PlanarWipping(bounceVelocity, wipping.getDragging()));
			setComp(e, new Physic(ph.getShape(), new ArrayList<>()));
		}
	}
}


































