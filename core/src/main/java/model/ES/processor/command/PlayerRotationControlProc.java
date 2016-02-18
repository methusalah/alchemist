package model.ES.processor.command;

import model.ES.component.ability.PlayerControl;
import model.ES.component.motion.PlanarNeededRotation;
import model.ES.component.motion.PlanarStance;
import model.tempImport.Command;
import model.tempImport.DataState;
import util.geometry.geom2d.Point2D;
import util.math.Angle;
import util.math.AngleUtil;

import com.jme3.app.state.AppStateManager;
import com.simsilica.es.Entity;

import main.java.model.ECS.pipeline.Processor;

public class PlayerRotationControlProc extends Processor {

	private Command command; 
	
	@Override
	protected void onInitialized(AppStateManager stateManager) {
		command = stateManager.getState(DataState.class).getCommand();
	}
	
	@Override
	protected void registerSets() {
		registerDefault(PlanarStance.class, PlayerControl.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		if(command.target == null)
			return;
		
		PlanarStance stance = e.get(PlanarStance.class);
		double angleToTarget = command.target.getSubtraction(stance.coord).getAngle();

		// rotation
		double neededRotAngle = 0;
		Point2D front = stance.coord.getTranslation(stance.orientation.getValue(), 1);
		int turn = AngleUtil.getTurn(stance.coord, front, command.target);
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
