package ECS.processor.rendering.particle;

import com.jme3.effect.ParticleEmitter;
import com.simsilica.es.Entity;

import ECS.component.assets.ParticleCaster;
import ECS.component.motion.PlanarStance;
import ECS.processor.SpatialPool;
import model.ECS.pipeline.Processor;
import model.tempImport.TranslateUtil;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;

public class ParticlePlacingProc extends Processor {
	
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

		ParticleEmitter pe = SpatialPool.emitters.get(e.getId());
		pe.setLocalTranslation(TranslateUtil.toVector3f(pos));
		pe.getParticleInfluencer().setInitialVelocity(TranslateUtil.toVector3f(velocity));
		
		// the all at once behavior is launched here, because the emitter needs to be well placed before that call.
		if(caster.isAllAtOnce())
			pe.emitAllParticles();
	}
}
