package test.java.processor.logic.command;

import model.ES.component.ability.PlayerControl;
import model.ES.component.motion.PlanarNeededThrust;
import model.ES.component.motion.PlanarStance;
import model.tempImport.Command;
import model.tempImport.DataState;
import util.math.AngleUtil;

import com.jme3.app.state.AppStateManager;
import com.simsilica.es.Entity;

import main.java.model.ECS.pipeline.Processor;

public class PlayerOrthogonalThrustControlProc extends Processor {

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
		if(!command.thrust.isOrigin()
				&& stance.coord.getDistance(command.target) > 0.1){
    		PlanarNeededThrust thrust = new PlanarNeededThrust(command.thrust.getRotation(AngleUtil.RIGHT));
    		setComp(e, thrust);
		}
	}
}
