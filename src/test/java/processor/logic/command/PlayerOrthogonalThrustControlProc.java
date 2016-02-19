package processor.logic.command;

import com.jme3.app.state.AppStateManager;
import com.simsilica.es.Entity;

import component.ability.PlayerControl;
import component.motion.PlanarNeededThrust;
import component.motion.PlanarStance;
import model.Command;
import model.ECS.pipeline.Processor;
import model.state.DataState;
import util.math.AngleUtil;

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
