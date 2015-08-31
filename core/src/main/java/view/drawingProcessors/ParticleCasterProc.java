package view.drawingProcessors;

import java.util.ArrayList;

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
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		PlanarStance stance = e.get(PlanarStance.class);
		ParticleCaster caster = e.get(ParticleCaster.class);
		
		if(!SpatialPool.emitters.containsKey(e.getId())){
			ParticleEmitter pe = new ParticleEmitter("dada", Type.Triangle, 50);
			SpatialPool.emitters.put(e.getId(), pe);

			// material
			Material m = new Material(AppFacade.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
			m.setTexture("Texture", AppFacade.getAssetManager().loadTexture("textures/" + "particles/flame.png"));
			pe.setMaterial(m);

			pe.setImagesX(2);
			pe.setImagesY(2);

			pe.getParticleInfluencer().setVelocityVariation((float)caster.getFanning());

			
			pe.setStartSize(0.5f);
			pe.setEndSize(0.2f);
			pe.setLowLife(0.1f);
			pe.setHighLife(0.5f);

			AppFacade.getRootNode().attachChild(pe);
		}
		ParticleEmitter pe = SpatialPool.emitters.get(e.getId());
		
		if(caster.isEmitting())
			pe.setParticlesPerSec((float)caster.getPerSecond());
		else
			pe.setParticlesPerSec(0);

		Point3D casterPos = caster.getTranslation().getRotationAroundZ(stance.getOrientation()).getAddition(stance.getCoord().get3D(1));//stance.getElevation()));
		Point3D velocity = caster.getDirection().getRotationAroundZ(stance.getOrientation());
		velocity = velocity.getScaled(caster.getInitialSpeed());
		
		pe.setLocalTranslation(TranslateUtil.toVector3f(casterPos));
		pe.getParticleInfluencer().setInitialVelocity(TranslateUtil.toVector3f(velocity));

		
		if(caster.getDuration() == 0)
			pe.emitAllParticles();

			
		
		
		

		// trick to interpolate position of the particles when emitter moves between two frames
		// as jMonkey doesn't manage it
		if(pe.getUserData("lastPos") != null &&
				!pe.getUserData("lastPos").equals(Vector3f.ZERO) &&
				!pe.getUserData("lastPos").equals(casterPos)){
//			double elapsedTime = System.currentTimeMillis()-(Long)pe.getUserData("lastTime");
			for(Particle p : getParticles(pe)){
				double age = (p.startlife-p.life)*1000;
				if(age < elapsedTime) {
					p.position.interpolate((Vector3f)pe.getUserData("lastPos"), (float)(age/elapsedTime));
				}
			}
		}
//		pe.setUserData("lastPos", pos.clone());
		pe.setUserData("lastTime", System.currentTimeMillis());

		
		
		
		
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
