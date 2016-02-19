package test.java.processor.rendering.particle;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.ui.TextField.OnscreenKeyboard;
import com.jme3.effect.Particle;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh.Type;
import com.jme3.effect.shapes.EmitterSphereShape;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector3f;
import com.simsilica.es.Entity;

import app.AppFacade;
import main.java.model.ECS.pipeline.Processor;
import model.ECS.Naming;
import model.ES.component.assets.ParticleCaster;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.SpaceStance;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import view.SpatialPool;
import view.jme.MyParticleEmitter;
import view.math.TranslateUtil;

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


//		// trick to interpolate position of the particles when emitter moves between two frames
//		// as jMonkey doesn't manage it
//		if(pe.getUserData("lastPos") != null &&
//				!pe.getUserData("lastPos").equals(Vector3f.ZERO) &&
//				!pe.getUserData("lastPos").equals(pe.getWorldTranslation())){
//			double myelapsed = System.currentTimeMillis()-(Long)pe.getUserData("lastTime");
//			int count = 0;
//			for(Particle p : getParticles(pe)){
//				double age = (p.startlife-p.life)*1000;
//				if(age < myelapsed) {
//					count++;
////					LogUtil.info("age : "+age+"/ elapsed : "+myelapsed);
//					p.position.set(pe.getWorldTranslation());
//					Vector3f save = p.position.clone();
//					p.position.interpolate((Vector3f)pe.getUserData("lastPos"), (float)(age/myelapsed));
////					LogUtil.info(age+" distance of interpolation : "+p.position.distance(save)+" / distance parcourue : "+save.distance((Vector3f)pe.getUserData("lastPos")));
//				}
//			}
////			if(count>0)
////				LogUtil.info("corrected particle count : "+count);
//		}
//		pe.setUserData("lastPos", pe.getWorldTranslation().clone());
//		pe.setUserData("lastTime", System.currentTimeMillis());
	}
	
	private ArrayList<Particle> getParticles(MyParticleEmitter pe){
		ArrayList<Particle> res = new ArrayList<>();
		for(int i = 0; i<pe.getParticles().length; i++){
			if(pe.getParticles()[i].life != 0) {
				res.add(pe.getParticles()[i]);
			}
		}
		return res;
	}

}
