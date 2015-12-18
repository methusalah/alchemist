package model.ES.processor.interaction;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.ECS.Processor;
import model.ES.component.LifeTime;
import model.ES.component.Naming;
import model.ES.component.ToRemove;
import model.ES.component.interaction.DamageOnTouch;
import model.ES.component.interaction.Damaging;
import model.ES.component.interaction.EffectOnTouch;
import model.ES.component.interaction.senses.Touching;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.physic.Collisioning;
import util.LogUtil;
import util.geometry.geom3d.Point3D;

public class TouchingClearingProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(Touching.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		removeComp(e, Touching.class);
	}
}
