package view.drawingProcessors;

import model.ES.component.visuals.ParticleCaster;
import view.SpatialPool;
import view.math.TranslateUtil;
import app.AppFacade;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh.Type;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class ParticleCasterProc extends Processor {

	@Override
	protected void registerSets() {
		register(ParticleCaster.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
		ParticleCaster caster = e.get(ParticleCaster.class);
		if(SpatialPool.emitters.containsKey(e.getId()))
			throw new RuntimeException("Can't add the same particle caster twice.");
		
		ParticleEmitter pe = new ParticleEmitter("ParticleCaster for entity "+e.getId(), Type.Triangle, caster.getMaxCount());
		SpatialPool.emitters.put(e.getId(), pe);

		// material
		Material m = new Material(AppFacade.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
		m.setTexture("Texture", AppFacade.getAssetManager().loadTexture("textures/" + caster.getSpritePath()));
		pe.setMaterial(m);

		pe.setImagesX(caster.getNbCol());
		pe.setImagesY(caster.getNbRow());

		// particle fanning
		pe.getParticleInfluencer().setVelocityVariation((float)caster.getFanning());

		// particle size
		pe.setStartSize((float)caster.getStartSize());
		pe.setEndSize((float)caster.getEndSize());
		
		// particle life
		pe.setLowLife((float)caster.getMinLife());
		pe.setHighLife((float)caster.getMaxLife());

		// particle color
		pe.setStartColor(TranslateUtil.toColorRGBA(caster.getStartColor()));
		pe.setEndColor(TranslateUtil.toColorRGBA(caster.getEndColor()));
		
		// particle facing
		switch(caster.getFacing()){
		case Camera: break;
		case Horizontal: pe.setFaceNormal(Vector3f.UNIT_Z); break;
		case Velocity: pe.setFacingVelocity(true);
		}
		
		//particle per seconds
		pe.setParticlesPerSec((float)caster.getPerSecond());
		
		AppFacade.getRootNode().attachChild(pe);
		
		if(caster.isAllAtOnce())
			pe.emitAllParticles();
	}
}
