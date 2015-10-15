package model.ES.processor.holder;

import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;

import controller.ECS.Processor;
import model.ModelManager;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.SpaceStance;
import model.ES.component.relation.Parenting;
import model.ES.component.relation.PlanarHolding;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.math.Angle;
import util.math.AngleUtil;

public class PlanarHoldingProc extends Processor {

	@Override
	protected void registerSets() {
		register(PlanarHolding.class, PlanarStance.class, Parenting.class);
		register(PlanarHolding.class, SpaceStance.class, Parenting.class);
	}

	@Override
	protected void onUpdated() {
    	for (Entity e : sets.get(0)){
    		managePlanar(e);
    	}
    	for (Entity e : sets.get(1)){
    		manageSpace(e);
    	}
	}

	private void managePlanar(Entity e) {
		PlanarHolding holded = e.get(PlanarHolding.class);
		Parenting parenting = e.get(Parenting.class);
		
		PlanarStance stance = entityData.getComponent(parenting.getParent(), PlanarStance.class);
		if(stance == null)
			return;

		Point2D newCoord = holded.localPosition.get2D().getRotation(stance.orientation.getValue());
		newCoord = newCoord.getAddition(stance.coord);
		double newOrientation = AngleUtil.normalize(stance.orientation.getValue() + holded.localOrientation.getValue());
		
		setComp(e, new PlanarStance(newCoord,
				new Angle(newOrientation),
				stance.elevation + holded.localPosition.z,
				stance.upVector));
	}

	private void manageSpace(Entity e) {
		PlanarHolding holded = e.get(PlanarHolding.class);
		Parenting parenting = e.get(Parenting.class);

		PlanarStance stance = entityData.getComponent(parenting.getParent(), PlanarStance.class);
		if(stance == null)
			return;
		
		Point2D newCoord = holded.localPosition.get2D().getRotation(stance.orientation.getValue());
		newCoord = newCoord.getAddition(stance.coord);
		double newOrientation = AngleUtil.normalize(stance.orientation.getValue() + holded.localOrientation.getValue());
		
		
		setComp(e, new SpaceStance(newCoord.get3D(stance.elevation + holded.localPosition.z),
				Point3D.UNIT_X.getRotationAroundZ(newOrientation)));
	}
}
