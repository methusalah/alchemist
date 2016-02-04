package view.drawingProcessors.particle;

import java.util.ArrayList;
import java.util.List;

import com.jme3.effect.ParticleMesh.Type;
import com.jme3.effect.shapes.EmitterSphereShape;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector3f;
import com.simsilica.es.Entity;

import app.AppFacade;
import controller.ECS.Processor;
import model.ES.component.motion.PlanarStance;
import model.ES.component.visuals.ParticleCaster;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import view.SpatialPool;
import view.jme.MyParticleEmitter;
import view.math.TranslateUtil;

public class ParticleProc extends Processor {
	List<MyParticleEmitter> toRemove = new ArrayList<MyParticleEmitter>(); 

	@Override
	protected void registerSets() {
		registerDefault(ParticleCaster.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		onEntityUpdated(e);
	}
	
	@Override
	protected void onEntityUpdated(Entity e) {
		if(SpatialPool.emitters.containsKey(e.getId()))
			AppFacade.getMainSceneNode().detachChild(SpatialPool.emitters.get(e.getId()));
		
		MyParticleEmitter pe = getEmiterFromCaster(e.get(ParticleCaster.class));
		
		SpatialPool.emitters.put(e.getId(), pe);
		AppFacade.getMainSceneNode().attachChild(pe);
	}
	
	@Override
	protected void onEntityRemoved(Entity e) {
		MyParticleEmitter pe = SpatialPool.emitters.get(e.getId());
		pe.setParticlesPerSec(0);
		toRemove.add(pe);
	}
	
	@Override
	protected void onUpdated() {
		// to remove the finished emitters
		for(MyParticleEmitter pe : new ArrayList<>(toRemove))
			if(pe.getNumVisibleParticles() == 0){
				AppFacade.getMainSceneNode().detachChild(pe);
				toRemove.remove(pe);
			}
	}
	
	private MyParticleEmitter getEmiterFromCaster(ParticleCaster caster){
		MyParticleEmitter res = new MyParticleEmitter("Particle caster", Type.Triangle, caster.getMaxCount());
		res.setParticlesPerSec(caster.getPerSecond());

		// material
		Material m = new Material(AppFacade.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
		res.setMaterial(m);
		if(!caster.getSpritePath().isEmpty())
			m.setTexture("Texture", AppFacade.getAssetManager().loadTexture("textures/" + caster.getSpritePath()));
		m.getAdditionalRenderState().setBlendMode(caster.isAdd()? RenderState.BlendMode.Additive : RenderState.BlendMode.AlphaAdditive);
		
		// sprite
		res.setImagesX(caster.getNbCol());
		res.setImagesY(caster.getNbRow());

		// fanning
		res.getParticleInfluencer().setVelocityVariation((float)caster.getFanning());

		// size
		res.setStartSize((float)caster.getStartSize());
		res.setEndSize((float)caster.getEndSize());
		
		// life
		res.setLowLife((float)caster.getMinLife());
		res.setHighLife((float)caster.getMaxLife());

		// color
		res.setStartColor(TranslateUtil.toColorRGBA(caster.getStartColor()));
		res.setEndColor(TranslateUtil.toColorRGBA(caster.getEndColor()));
		
		// facing
		switch(caster.getFacing()){
		case Camera: break;
		case Horizontal: res.setFaceNormal(Vector3f.UNIT_Z); break;
		case Velocity: res.setFacingVelocity(true);
		}

		if(caster.getStartVariation() != 0) {
			res.setShape(new EmitterSphereShape(Vector3f.ZERO, (float)caster.getStartVariation()));
		}
		
		res.setGravity(caster.isGravity()? Vector3f.UNIT_Z.negate() : Vector3f.ZERO);
		
		if(caster.isAllAtOnce())
			res.emitAllParticles();
		return res;
	}
}
