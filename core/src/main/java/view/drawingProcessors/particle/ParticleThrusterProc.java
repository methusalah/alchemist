package view.drawingProcessors.particle;

import com.simsilica.es.Entity;

import controller.ECS.Processor;
import model.ES.commonLogic.Controlling;
import model.ES.component.assets.RotationThruster;
import model.ES.component.assets.Thruster;
import model.ES.component.hierarchy.ThrusterControl;
import model.ES.component.visuals.ParticleCaster;
import util.LogUtil;
import util.math.Fraction;
import view.SpatialPool;
import view.jme.MyParticleEmitter;

public class ParticleThrusterProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(ParticleCaster.class, ThrusterControl.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		ParticleCaster caster = e.get(ParticleCaster.class);
		ThrusterControl control = e.get(ThrusterControl.class);
		if(control.isActive()){
			MyParticleEmitter pe = SpatialPool.emitters.get(e.getId());
			Thruster t = Controlling.getControl(Thruster.class, e.getId(), entityData);
			RotationThruster rt = Controlling.getControl(RotationThruster.class, e.getId(), entityData);
			
			double activationRate;
			if(rt != null)
				activationRate = rt.activation.getValue();
			else if(t != null)
				activationRate = t.activation.getValue();
			else{
				LogUtil.warning("Can't find the controlling thruster for thruster controlled entity "+ e.getId());
				return;
			}
			pe.setParticlesPerSec((int)Math.round(caster.getPerSecond()*activationRate));

		}
	}
}
