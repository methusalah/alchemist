package model.ES.processor.holder;

import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;

import controller.entityAppState.Processor;
import model.ModelManager;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.SpaceStance;
import model.ES.component.relation.Parenting;
import model.ES.component.relation.PlanarHolding;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.math.AngleUtil;

public class PlanarHoldingProc extends Processor {

	@Override
	protected void registerSets() {
		register(PlanarHolding.class, PlanarStance.class, Parenting.class);
		register(PlanarHolding.class, SpaceStance.class, Parenting.class);
	}

	@Override
	protected void onUpdated(float elapsedTime) {
		if(ModelManager.command.target == null)
			return;
		
    	for (Entity e : sets.get(0)){
    		managePlanar(e, elapsedTime);
    	}
    	for (Entity e : sets.get(1)){
    		manageSpace(e, elapsedTime);
    	}
	}

	private void managePlanar(Entity e, float elapsedTime) {
		PlanarHolding holded = e.get(PlanarHolding.class);
		Parenting parenting = e.get(Parenting.class);
		
		PlanarStance stance = entityData.getComponent(parenting.parent, PlanarStance.class);
		if(stance == null){
			entityData.removeEntity(e.getId());
		} else {
			Point2D newCoord = holded.localPosition.get2D().getRotation(stance.orientation);
			newCoord = newCoord.getAddition(stance.coord);
			double newOrientation = AngleUtil.normalize(stance.orientation + holded.localOrientation);
			
			setComp(e, new PlanarStance(newCoord,
					newOrientation,
					stance.elevation + holded.localPosition.z,
					stance.upVector));
		}
	}

	private void manageSpace(Entity e, float elapsedTime) {
		PlanarHolding holded = e.get(PlanarHolding.class);
		Parenting parenting = e.get(Parenting.class);

		PlanarStance stance = entityData.getComponent(parenting.parent, PlanarStance.class);
		Point2D newCoord = holded.localPosition.get2D().getRotation(stance.orientation);
		newCoord = newCoord.getAddition(stance.coord);
		double newOrientation = AngleUtil.normalize(stance.orientation + holded.localOrientation);
		
		
		setComp(e, new SpaceStance(newCoord.get3D(stance.elevation + holded.localPosition.z),
				Point3D.UNIT_X.getRotationAroundZ(newOrientation)));
	}
}
