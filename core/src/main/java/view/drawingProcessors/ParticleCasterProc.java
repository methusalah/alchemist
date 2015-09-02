package view.drawingProcessors;

import java.util.ArrayList;

import util.LogUtil;
import util.geometry.geom3d.Point3D;
import view.SpatialPool;
import view.math.TranslateUtil;
import model.ES.component.motion.PlanarStance;
import model.ES.component.visuals.ParticleCaster;
import app.AppFacade;

import com.jme3.effect.Particle;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh.Type;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class ParticleCasterProc extends Processor {

	@Override
	protected void registerSets() {
		register(ParticleCaster.class, PlanarStance.class);
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
		
		onEntityUpdated(e, elapsedTime);
	}

	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		PlanarStance stance = e.get(PlanarStance.class);
		ParticleCaster caster = e.get(ParticleCaster.class);
		ParticleEmitter pe = SpatialPool.emitters.get(e.getId());

		Point3D casterPos = caster.getTranslation().getRotationAroundZ(stance.getOrientation()).getAddition(stance.getCoord().get3D(stance.getElevation()));
		Point3D velocity = caster.getDirection().getRotationAroundZ(stance.getOrientation());
		velocity = velocity.getScaled(caster.getInitialSpeed());
		
		pe.setLocalTranslation(TranslateUtil.toVector3f(casterPos));
		pe.getParticleInfluencer().setInitialVelocity(TranslateUtil.toVector3f(velocity));

		if(caster.isAllAtOnce())
			pe.emitAllParticles();

		// trick to interpolate position of the particles when emitter moves between two frames
		// as jMonkey doesn't manage it
		if(pe.getUserData("lastPos") != null &&
				!pe.getUserData("lastPos").equals(Vector3f.ZERO) &&
				!pe.getUserData("lastPos").equals(pe.getWorldTranslation())){
			double myelapsed = System.currentTimeMillis()-(Long)pe.getUserData("lastTime");
			int count = 0;
			for(Particle p : getParticles(pe)){
				double age = (p.startlife-p.life)*1000;
				if(age < myelapsed) {
					count++;
//					LogUtil.info("age : "+age+"/ elapsed : "+myelapsed);
					p.position.set(pe.getWorldTranslation());
					Vector3f save = p.position.clone();
					p.position.interpolate((Vector3f)pe.getUserData("lastPos"), (float)(age/myelapsed));
					LogUtil.info(age+" distance of interpolation : "+p.position.distance(save)+" / distance parcourue : "+save.distance((Vector3f)pe.getUserData("lastPos")));
				}
			}
			if(count>0)
				LogUtil.info("corrected particle count : "+count);
		}
		pe.setUserData("lastPos", pe.getWorldTranslation().clone());
		pe.setUserData("lastTime", System.currentTimeMillis());
	}

	@Override
	protected void onEntityRemoved(Entity e, float elapsedTime) {
		
	}

	private ArrayList<Particle> getParticles(ParticleEmitter pe){
		ArrayList<Particle> res = new ArrayList<>();
		for(int i = 0; i<pe.getParticles().length; i++){
			if(pe.getParticles()[i].life != 0) {
				res.add(pe.getParticles()[i]);
			}
		}
		return res;
	}

}
