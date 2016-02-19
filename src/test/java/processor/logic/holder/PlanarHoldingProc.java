package processor.logic.holder;

import com.simsilica.es.Entity;

import javafx.geometry.Point3D;
import model.ECS.pipeline.Processor;

public class PlanarHoldingProc extends Processor {

	@Override
	protected void registerSets() {
		register("planarHolded", PlanarStanceControl.class, PlanarStance.class);
		register("spaceHolded", PlanarStanceControl.class, SpaceStance.class);
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
		PlanarStanceControl control = e.get(PlanarStanceControl.class);
		
		if(control.isActive()){
			PlanarStance parentStance = Controlling.getControl(PlanarStance.class, e.getId(), entityData);
			if(parentStance == null)
				return;
		
			Point2D newCoord = control.localPosition.get2D().getRotation(parentStance.orientation.getValue());
			newCoord = newCoord.getAddition(parentStance.coord);
			double newOrientation = AngleUtil.normalize(parentStance.orientation.getValue() + control.localOrientation.getValue());
			
			setComp(e, new PlanarStance(newCoord,
					new Angle(newOrientation),
					parentStance.elevation + control.localPosition.z,
					parentStance.upVector));
		}
	}

	private void manageSpace(Entity e) {
		PlanarStanceControl control = e.get(PlanarStanceControl.class);

		if(control.isActive()){
			PlanarStance parentStance = Controlling.getControl(PlanarStance.class, e.getId(), entityData);
			if(parentStance == null)
				return;
			
			Point2D newCoord = control.localPosition.get2D().getRotation(parentStance.orientation.getValue());
			newCoord = newCoord.getAddition(parentStance.coord);
			double newOrientation = AngleUtil.normalize(parentStance.orientation.getValue() + control.localOrientation.getValue());
			
			
			setComp(e, new SpaceStance(newCoord.get3D(parentStance.elevation + control.localPosition.z),
					Point3D.UNIT_X.getRotationAroundZ(newOrientation)));
		}
	}
}
