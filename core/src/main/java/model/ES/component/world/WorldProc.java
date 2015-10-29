package model.ES.component.world;

import com.simsilica.es.Entity;

import model.ES.component.command.PlayerControl;
import model.ES.component.motion.PlanarStance;
import controller.ECS.Processor;

public class WorldProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(PlayerControl.class, PlanarStance);
	}
	
	@Override
	protected void onEntityUpdated(Entity e) {
		// TODO Auto-generated method stub
		super.onEntityUpdated(e);
	}
	
	

}
