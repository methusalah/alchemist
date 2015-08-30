package view.drawingProcessors;

import util.geometry.geom3d.Point3D;
import view.SpatialPool;
import view.math.TranslateUtil;
import model.ES.component.motion.PlanarStance;
import model.ES.component.visuals.ParticleCaster;

import com.jme3.effect.ParticleEmitter;
import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class ParticleCasterProc extends Processor {

	@Override
	protected void registerSets() {
		register(ParticleCaster.class, PlanarStance.class);
	}
	
	
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		PlanarStance stance = e.get(PlanarStance.class);
		ParticleCaster caster = e.get(ParticleCaster.class);
		
		if(!SpatialPool.emitters.containsKey(e.getId()))
			SpatialPool.emitters.put(e.getId(), new ParticleEmitter());
		ParticleEmitter pe = SpatialPool.emitters.get(e.getId());
		
		Point3D casterPos = caster.getTranslation().getRotationAroundZ(stance.getOrientation()).getAddition(stance.getCoord().get3D(0));
		Point3D velocity = caster.getDirection().getRotationAroundZ(stance.getOrientation());
		velocity = velocity.getScaled(caster.getInitialSpeed());
		
		pe.setLocalTranslation(TranslateUtil.toVector3f(casterPos));
		pe.getParticleInfluencer().setInitialVelocity(TranslateUtil.toVector3f(velocity));
		pe.getParticleInfluencer().setVelocityVariation((float)caster.getFanning());

		if(caster.isEmitting())
			pe.setParticlesPerSec((float)caster.getPerSecond());
		else
			pe.setParticlesPerSec(0);
		
		if(caster.getDuration() == 0)
			pe.emitAllParticles();

			

		
		
		
		
		
	}
}
