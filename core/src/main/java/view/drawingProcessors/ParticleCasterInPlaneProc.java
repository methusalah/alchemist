package view.drawingProcessors;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.ui.TextField.OnscreenKeyboard;
import com.jme3.effect.Particle;
import com.jme3.effect.ParticleMesh.Type;
import com.jme3.effect.shapes.EmitterSphereShape;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector3f;
import com.simsilica.es.Entity;

import app.AppFacade;
import controller.ECS.Processor;
import model.ES.component.Naming;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.SpaceStance;
import model.ES.component.visuals.ParticleCaster;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import view.SpatialPool;
import view.jme.MyParticleEmitter;
import view.math.TranslateUtil;

public class ParticleCasterInPlaneProc extends Processor {

	List<MyParticleEmitter> toRemove = new ArrayList<MyParticleEmitter>(); 
	
	@Override
	protected void registerSets() {
		registerDefault(ParticleCaster.class, PlanarStance.class);
	}
	
	@Override
	protected void onUpdated() {
		// just to remove the finished emitter
		List<MyParticleEmitter> detached = new ArrayList<>();
		for(MyParticleEmitter pe : toRemove)
			if(pe.getNumVisibleParticles() == 0){
				AppFacade.getMainSceneNode().detachChild(pe);
				detached.add(pe);
			}
		toRemove.removeAll(detached);
	}

	@Override
	protected void onEntityAdded(Entity e) {
		ParticleCaster caster = e.get(ParticleCaster.class);
		MyParticleEmitter pe = new MyParticleEmitter("ParticleCaster for entity "+e.getId(), Type.Triangle, caster.getMaxCount());

		PlanarStance stance = e.get(PlanarStance.class);
		Point3D pos = stance.getCoord().get3D(stance.elevation);
		Point3D velocity = Point2D.ORIGIN.getTranslation(stance.orientation.getValue(), caster.getInitialSpeed()).get3D(0);

		pe.setLocalTranslation(TranslateUtil.toVector3f(pos));
		pe.getParticleInfluencer().setInitialVelocity(TranslateUtil.toVector3f(velocity));
		
		Material m = new Material(AppFacade.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
		pe.setMaterial(m);
		pe.setParticlesPerSec(0);

		SpatialPool.emitters.put(e.getId(), pe);
		AppFacade.getMainSceneNode().attachChild(pe);
		onEntityUpdated(e);
	}
	
	@Override
	protected void onEntityUpdated(Entity e) {
		updateEmitterParam(e);
		updateEmitterPosition(e);
	}
	
	private void updateEmitterParam(Entity e){
		ParticleCaster caster = e.get(ParticleCaster.class);
		MyParticleEmitter pe = SpatialPool.emitters.get(e.getId());

		// if the max count has change, we have to recreate the emitter
		if(caster.getMaxCount() != pe.getMaxNumParticles()){
			AppFacade.getMainSceneNode().detachChild(pe);
			onEntityAdded(e);
			pe = SpatialPool.emitters.get(e.getId());
		}

		// material
		if(!caster.getSpritePath().isEmpty())
			pe.getMaterial().setTexture("Texture", AppFacade.getAssetManager().loadTexture("textures/" + caster.getSpritePath()));
		pe.getMaterial().getAdditionalRenderState().setBlendMode(caster.isAdd()? RenderState.BlendMode.Additive : RenderState.BlendMode.AlphaAdditive);
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

		if(caster.getStartVariation() != 0) {
			pe.setShape(new EmitterSphereShape(Vector3f.ZERO, (float)caster.getStartVariation()));
		}
		
		pe.setGravity(Vector3f.ZERO);
		
		if(caster.isAllAtOnce())
			pe.emitAllParticles();
	}
	
	private void updateEmitterPosition(Entity e){
		ParticleCaster caster = e.get(ParticleCaster.class);
		PlanarStance stance = e.get(PlanarStance.class);
		Point3D pos = stance.getCoord().get3D(stance.elevation);
		Point3D velocity = Point2D.ORIGIN.getTranslation(stance.orientation.getValue(), caster.getInitialSpeed()).get3D(0);

		MyParticleEmitter pe = SpatialPool.emitters.get(e.getId());
		
		pe.setLocalTranslation(TranslateUtil.toVector3f(pos));
		pe.getParticleInfluencer().setInitialVelocity(TranslateUtil.toVector3f(velocity));
		pe.setParticlesPerSec((int)Math.round(caster.getPerSecond()*caster.getActualPerSecond().getValue()));

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
	
	@Override
	protected void onEntityRemoved(Entity e) {
		MyParticleEmitter pe = SpatialPool.emitters.get(e.getId());
		pe.setParticlesPerSec(0);
		toRemove.add(pe);
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
