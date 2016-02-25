package logic.processor.logic.command;

import com.simsilica.es.Entity;

import command.CommandPlatform;
import component.ability.PlayerControl;
import component.motion.PlanarNeededRotation;
import component.motion.PlanarStance;
import model.ECS.pipeline.Processor;
import util.geometry.geom2d.Point2D;
import util.math.Angle;
import util.math.AngleUtil;

public class PlayerRotationControlProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(PlanarStance.class, PlayerControl.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		if(CommandPlatform.target == null)
			return;
		
		PlanarStance stance = e.get(PlanarStance.class);
		double angleToTarget = CommandPlatform.target.getSubtraction(stance.coord).getAngle();

		// rotation
		double neededRotAngle = 0;
		Point2D front = stance.coord.getTranslation(stance.orientation.getValue(), 1);
		int turn = AngleUtil.getTurn(stance.coord, front, CommandPlatform.target);
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
