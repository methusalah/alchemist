package logic.processor.logic.command;

import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.simsilica.es.Entity;

import command.CommandPlatform;
import component.ability.PlayerControl;
import component.motion.PlanarNeededThrust;
import component.motion.PlanarStance;
import util.math.AngleUtil;

public class PlayerOrthogonalThrustControlProc extends BaseProcessor {

	@Override
	protected void registerSets() {
		registerDefault(PlanarStance.class, PlayerControl.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		if(CommandPlatform.target == null)
			return;

		PlanarStance stance = e.get(PlanarStance.class);
		if(!CommandPlatform.thrust.isOrigin()
				&& stance.coord.getDistance(CommandPlatform.target) > 0.1){
    		PlanarNeededThrust thrust = new PlanarNeededThrust(CommandPlatform.thrust.getRotation(AngleUtil.RIGHT));
    		setComp(e, thrust);
		}
	}
}
