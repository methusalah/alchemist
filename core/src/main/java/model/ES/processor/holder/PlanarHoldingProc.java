package model.ES.processor.holder;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import controller.ECS.Processor;
import model.ModelManager;
import model.ES.commonLogic.Controlling;
import model.ES.component.hierarchy.Parenting;
import model.ES.component.hierarchy.PlanarStanceControl;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.SpaceStance;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.math.Angle;
import util.math.AngleUtil;

public class PlanarHoldingProc extends Processor {

	@Override
	protected void registerSets() {
		register("planarHolded", PlanarStanceControl.class, PlanarStance.class, Parenting.class);
		register("spaceHolded", PlanarStanceControl.class, SpaceStance.class, Parenting.class);
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
		PlanarStanceControl holded = e.get(PlanarStanceControl.class);
		
		PlanarStance parentStance = Controlling.getControl(PlanarStance.class, e.getId(), entityData);
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
		PlanarStanceControl holded = e.get(PlanarStanceControl.class);

		PlanarStance parentStance = Controlling.getControl(PlanarStance.class, e.getId(), entityData);
		if(parentStance == null)
			return;
		
		Point2D newCoord = holded.localPosition.get2D().getRotation(parentStance.orientation.getValue());
		newCoord = newCoord.getAddition(parentStance.coord);
		double newOrientation = AngleUtil.normalize(parentStance.orientation.getValue() + holded.localOrientation.getValue());
		
		
		setComp(e, new SpaceStance(newCoord.get3D(parentStance.elevation + holded.localPosition.z),
				Point3D.UNIT_X.getRotationAroundZ(newOrientation)));
	}
}
