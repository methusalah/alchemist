package model.ES.processor.shipGear;

import com.simsilica.es.Entity;

import controller.ECS.Processor;
import model.ES.commonLogic.Controlling;
import model.ES.component.assets.RotationThruster;
import model.ES.component.assets.Thruster;
import model.ES.component.hierarchy.ThrusterControl;
import model.ES.component.visuals.ParticleCaster;
import util.LogUtil;
import util.math.Fraction;

public class ParticleThrusterProc extends Processor {

	@Override
	protected void registerSets() {
		register("rotation", ParticleCaster.class, ThrusterControl.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		ParticleCaster c = e.get(ParticleCaster.class);
		ThrusterControl control = e.get(ThrusterControl.class);
		
		if(control.isActive()){
			Thruster t = Controlling.getControl(Thruster.class, e.getId(), entityData);
			RotationThruster rt = Controlling.getControl(RotationThruster.class, e.getId(), entityData);
			
			double activationRate;
			if(rt != null)
				activationRate = rt.activation.getValue();
			else if(t != null)
				activationRate = t.activation.getValue();
			else
				return;
			
			setComp(e, new ParticleCaster(c.getSpritePath(),
					c.getNbCol(),
					c.getNbRow(),
					c.getInitialSpeed(),
					c.getFanning(),
					c.isRandomSprite(),
					c.getMaxCount(),
					c.getPerSecond(),
					c.getStartSize(),
					c.getEndSize(),
					c.getStartColor(),
					c.getEndColor(),
					c.getMinLife(),
					c.getMaxLife(),
					c.getRotationSpeed(),
					c.isGravity(),
					c.getFacing(),
					c.isAdd(),
					c.getStartVariation(),
					c.isAllAtOnce(),
					new Fraction(activationRate)));
		}
	}
}
