package logic.processor.rendering.particle;

import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.brainless.alchemist.model.tempImport.TranslateUtil;
import com.jme3.effect.ParticleEmitter;
import com.simsilica.es.Entity;

import component.assets.ParticleCaster;
import component.motion.PlanarStance;
import logic.processor.Pool;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;

public class ParticlePlacingProc extends BaseProcessor {
	
	@Override
	protected void registerSets() {
		registerDefault(ParticleCaster.class, PlanarStance.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		onEntityUpdated(e);
	}
	
	@Override
	protected void onEntityUpdated(Entity e) {
		ParticleCaster caster = e.get(ParticleCaster.class);
		PlanarStance stance = e.get(PlanarStance.class);
		
		Point3D pos = stance.getCoord().get3D(stance.elevation);
		Point3D velocity = Point2D.ORIGIN.getTranslation(stance.orientation.getValue(), caster.getInitialSpeed()).get3D(0);

		ParticleEmitter pe = Pool.emitters.get(e.getId());
		pe.setLocalTranslation(TranslateUtil.toVector3f(pos));
		pe.getParticleInfluencer().setInitialVelocity(TranslateUtil.toVector3f(velocity));
		
		// the all at once behavior is launched here, because the emitter needs to be well placed before that call.
		if(caster.isAllAtOnce())
			pe.emitAllParticles();
	}
}
