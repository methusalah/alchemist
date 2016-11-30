package logic.processor.logic.command;

import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.simsilica.es.Entity;

import command.CommandPlatform;
import component.ability.PlayerControl;
import component.motion.PlanarNeededThrust;
import component.motion.PlanarStance;

public class PlayerThrustControlProc extends BaseProcessor {

	@Override
	protected void registerSets() {
		registerDefault(PlanarStance.class, PlayerControl.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		if(CommandPlatform.target == null)
			return;
		
		PlanarStance stance = e.get(PlanarStance.class);
		if(stance.coord.getDistance(CommandPlatform.target) > 0.1){
    		PlanarNeededThrust thrust = new PlanarNeededThrust(CommandPlatform.thrust.getRotation(stance.orientation.getValue()));
    		setComp(e, thrust);
		}
	}
}
