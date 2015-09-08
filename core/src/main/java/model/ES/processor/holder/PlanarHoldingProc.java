package model.ES.processor.holder;

import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;
import model.ModelManager;
import model.ES.component.planarMotion.PlanarStance;
import model.ES.component.relation.BoneHolding;
import model.ES.component.relation.PlanarHolding;
import model.ES.component.spaceMotion.SpaceStance;
import model.ES.component.visuals.Skeleton;

import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;

import controller.entityAppState.Processor;

public class PlanarHoldingProc extends Processor {

	@Override
	protected void registerSets() {
		register(PlanarHolding.class, PlanarStance.class);
	}

	@Override
	protected void onUpdated(float elapsedTime) {
		if(ModelManager.command.target == null)
			return;
		
        for(EntitySet set : sets)
        	for (Entity e : set){
        		manage(e, elapsedTime);
        	}
	}

	private void manage(Entity e, float elapsedTime) {
		PlanarHolding holded = e.get(PlanarHolding.class);
		PlanarStance stance = entityData.getComponent(holded.getHolder(), PlanarStance.class);
		Point2D newCoord = holded.getLocalPosition().get2D().getRotation(stance.getOrientation());
		newCoord = newCoord.getAddition(stance.getCoord());
		double newOrientation = AngleUtil.normalize(stance.getOrientation() + holded.getLocalOrientation());
		
		
		setComp(e, new PlanarStance(newCoord,
				newOrientation,
				stance.getElevation() + holded.getLocalPosition().z,
				stance.getUpVector()));
	}
}
