package model.ES.processor.command;

import model.ModelManager;
import model.ES.component.command.PlanarNeededRotation;
import model.ES.component.command.PlayerControl;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.PlanarVelocityToApply;
import util.geometry.geom2d.Point2D;
import util.math.Angle;
import util.math.AngleUtil;

import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;

import controller.ECS.Processor;

public class PlayerRotationControlProc extends Processor {
	
	@Override
	protected void registerSets() {
		registerDefault(PlanarStance.class, PlayerControl.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		if(ModelManager.command.target == null)
			return;
		
		PlanarStance stance = e.get(PlanarStance.class);
		double angleToTarget = ModelManager.command.target.getSubtraction(stance.coord).getAngle();

		// rotation
		double neededRotAngle = 0;
		Point2D front = stance.coord.getTranslation(stance.orientation.getValue(), 1);
		int turn = AngleUtil.getTurn(stance.coord, front, ModelManager.command.target);
		if(turn != AngleUtil.NONE){// || angleToTarget != stance.getOrientation()){
			double diff = AngleUtil.getSmallestDifference(stance.orientation.getValue(), angleToTarget);
			if(turn >= 0)
				neededRotAngle = diff;
			else
				neededRotAngle = -diff;
		}
		
		if(Math.abs(neededRotAngle) > 0.01){
			PlanarNeededRotation neededRotation = new PlanarNeededRotation(new Angle(neededRotAngle));
    		setComp(e, neededRotation);
		}
	}
}
