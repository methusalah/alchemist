package logic.processor.rendering.particle;

import com.brainless.alchemist.model.ECS.pipeline.Processor;
import com.jme3.effect.ParticleEmitter;
import com.simsilica.es.Entity;

import component.assets.ParticleCaster;
import component.motion.RotationThruster;
import component.motion.Thruster;
import component.motion.ThrusterControl;
import logic.commonLogic.Controlling;
import logic.processor.SpatialPool;
import util.LogUtil;

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
			ParticleEmitter pe = SpatialPool.emitters.get(e.getId());
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
