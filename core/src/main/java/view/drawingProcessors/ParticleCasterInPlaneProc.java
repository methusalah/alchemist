package view.drawingProcessors;

import java.util.ArrayList;
import java.util.List;

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
		List<MyParticleEmitter> detached = new ArrayList<>();
		for(MyParticleEmitter pe : toRemove)
			if(pe.getParticles().length == 0){
				AppFacade.getRootNode().detachChild(pe);
				detached.add(pe);
			}
		toRemove.removeAll(detached);
	}

	@Override
	protected void onEntityEachTick(Entity e) {
		onEntityAdded(e);
		Point3D pos, velocity;
		if(e.get(PlanarStance.class) != null){
			PlanarStance stance = e.get(PlanarStance.class);
			pos = stance.coord.get3D(stance.elevation);
			velocity = Point3D.UNIT_X.getRotationAroundZ(stance.orientation.getValue());
		} else if(e.get(SpaceStance.class) != null){ 
			SpaceStance stance = e.get(SpaceStance.class);
			pos = stance.position;
			velocity = stance.direction;
		} else {
			throw new RuntimeException("Stance missing for a caster.");
		}

		ParticleCaster caster = e.get(ParticleCaster.class);
		MyParticleEmitter pe = SpatialPool.emitters.get(e.getId());
		
		velocity = velocity.getScaled(caster.getInitialSpeed());
		
		pe.setLocalTranslation(TranslateUtil.toVector3f(pos));
		pe.getParticleInfluencer().setInitialVelocity(TranslateUtil.toVector3f(velocity));
		pe.setParticlesPerSec((int)Math.round(caster.getPerSecond()*caster.getActualPerSecond().getValue()));

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
//					LogUtil.info(age+" distance of interpolation : "+p.position.distance(save)+" / distance parcourue : "+save.distance((Vector3f)pe.getUserData("lastPos")));
				}
			}
//			if(count>0)
//				LogUtil.info("corrected particle count : "+count);
		}
		pe.setUserData("lastPos", pe.getWorldTranslation().clone());
		pe.setUserData("lastTime", System.currentTimeMillis());
	}

	@Override
	protected void onEntityAdded(Entity e) {
		ParticleCaster casting = e.get(ParticleCaster.class);
		
		if(!SpatialPool.emitters.containsKey(e.getId())){
			MyParticleEmitter pe = new MyParticleEmitter("ParticleCaster for entity "+e.getId(), Type.Triangle, casting.getMaxCount());
			SpatialPool.emitters.put(e.getId(), pe);
		}
		
		MyParticleEmitter pe = SpatialPool.emitters.get(e.getId());
		if(casting.getMaxCount() != pe.getMaxNumParticles()){
			AppFacade.getRootNode().detachChild(pe);
			pe = new MyParticleEmitter("ParticleCaster for entity "+e.getId(), Type.Triangle, casting.getMaxCount());
			SpatialPool.emitters.put(e.getId(), pe);
		}
		if(!AppFacade.getRootNode().hasChild(pe))
			AppFacade.getRootNode().attachChild(pe);

		// material
		Material m = new Material(AppFacade.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
		if(!casting.getSpritePath().isEmpty())
			m.setTexture("Texture", AppFacade.getAssetManager().loadTexture("textures/" + casting.getSpritePath()));
		
		if(!casting.isAdd()) {
			m.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.AlphaAdditive);
		} else {
			m.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Additive);
		}
		pe.setMaterial(m);

		pe.setImagesX(casting.getNbCol());
		pe.setImagesY(casting.getNbRow());

		// particle fanning
		pe.getParticleInfluencer().setVelocityVariation((float)casting.getFanning());

		// particle size
		pe.setStartSize((float)casting.getStartSize());
		pe.setEndSize((float)casting.getEndSize());
		
		// particle life
		pe.setLowLife((float)casting.getMinLife());
		pe.setHighLife((float)casting.getMaxLife());

		// particle color
		pe.setStartColor(TranslateUtil.toColorRGBA(casting.getStartColor()));
		pe.setEndColor(TranslateUtil.toColorRGBA(casting.getEndColor()));
		
		// particle facing
		switch(casting.getFacing()){
		case Camera: break;
		case Horizontal: pe.setFaceNormal(Vector3f.UNIT_Z); break;
		case Velocity: pe.setFacingVelocity(true);
		}

		if(casting.getStartVariation() != 0) {
			pe.setShape(new EmitterSphereShape(Vector3f.ZERO, (float)casting.getStartVariation()));
		}
		
		pe.setGravity(Vector3f.ZERO);
		//particle per seconds
		pe.setParticlesPerSec((float)casting.getActualPerSecond().getValue());
		
		if(casting.isAllAtOnce())
			pe.emitAllParticles();
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
