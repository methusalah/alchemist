package view.drawingProcessors;

import java.util.ArrayList;

import util.LogUtil;
import util.geometry.geom3d.Point3D;
import view.SpatialPool;
import view.math.TranslateUtil;
import model.ES.component.planarMotion.PlanarStance;
import model.ES.component.spaceMotion.SpaceStance;
import model.ES.component.visuals.ParticleCaster;
import app.AppFacade;

import com.jme3.effect.Particle;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh.Type;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class ParticleCasterInSpaceProc extends Processor {

	@Override
	protected void registerSets() {
		register(ParticleCaster.class, SpaceStance.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
		onEntityUpdated(e, elapsedTime);
	}

	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		SpaceStance stance = e.get(SpaceStance.class);
		ParticleCaster caster = e.get(ParticleCaster.class);
		ParticleEmitter pe = SpatialPool.emitters.get(e.getId());

		Point3D casterPos = caster.getTranslation().getad
		Point3D velocity = caster.getDirection().getRotationAroundZ(stance.getOrientation());
		velocity = velocity.getScaled(caster.getInitialSpeed());
		
		pe.setLocalTranslation(TranslateUtil.toVector3f(casterPos));
		pe.getParticleInfluencer().setInitialVelocity(TranslateUtil.toVector3f(velocity));


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
