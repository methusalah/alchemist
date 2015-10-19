package model.ES.processor.holder;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;
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
		register("planarHolded", PlanarHolding.class, PlanarStance.class, Parenting.class);
		register("spaceHolded", PlanarHolding.class, SpaceStance.class, Parenting.class);
		register("holders", PlanarStance.class);
	}

	@Override
	protected void onUpdated() {
    	for (Entity e : getSet("planarHolded")){
    		managePlanar(e);
    	}
    	for (Entity e : getSet("spaceHolded")){
    		manageSpace(e);
    	}
	}

	private void managePlanar(Entity e) {
		PlanarHolding holded = e.get(PlanarHolding.class);
		Parenting parenting = e.get(Parenting.class);
		
		Entity parent = getSet("holders").getEntity(parenting.getParent());
		if(parent == null)
			return;
		PlanarStance parentStance = parent.get(PlanarStance.class);
		if(parentStance == null)
			return;

		Point2D newCoord = holded.localPosition.get2D().getRotation(parentStance.orientation.getValue());
		newCoord = newCoord.getAddition(parentStance.coord);
		double newOrientation = AngleUtil.normalize(parentStance.orientation.getValue() + holded.localOrientation.getValue());
		
		setComp(e, new PlanarStance(newCoord,
				new Angle(newOrientation),
				parentStance.elevation + holded.localPosition.z,
				parentStance.upVector));
	}

	private void manageSpace(Entity e) {
		PlanarHolding holded = e.get(PlanarHolding.class);
		Parenting parenting = e.get(Parenting.class);

		Entity parent = getSet("holders").getEntity(parenting.getParent());
		if(parent == null)
			return;
		PlanarStance parentStance = parent.get(PlanarStance.class);
		if(parentStance == null)
			return;
		
		Point2D newCoord = holded.localPosition.get2D().getRotation(parentStance.orientation.getValue());
		newCoord = newCoord.getAddition(parentStance.coord);
		double newOrientation = AngleUtil.normalize(parentStance.orientation.getValue() + holded.localOrientation.getValue());
		
		
		setComp(e, new SpaceStance(newCoord.get3D(parentStance.elevation + holded.localPosition.z),
				Point3D.UNIT_X.getRotationAroundZ(newOrientation)));
	}
}
