package view.drawingProcessors.audio;

import java.util.HashMap;
import java.util.Map;

import com.jme3.audio.AudioNode;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Spatial;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import app.AppFacade;
import controller.ECS.Processor;
import model.ES.commonLogic.Controlling;
import model.ES.component.Naming;
import model.ES.component.Parenting;
import model.ES.component.ability.Ability;
import model.ES.component.ability.AbilityControl;
import model.ES.component.assets.AudioSource;
import model.ES.component.assets.Model;
import model.ES.component.assets.ThrusterAudioSource;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.Thruster;
import model.ES.component.motion.ThrusterControl;
import util.LogUtil;
import view.SpatialPool;

public class AudioSourceProc extends Processor {
	Map<String, AudioNode> soundPrototypes = new HashMap<>();

	@Override
	protected void registerSets() {
		registerDefault(AudioSource.class);
	}

	@Override
	protected void onEntityAdded(Entity e) {
		onEntityUpdated(e);
	}

	@Override
	protected void onEntityUpdated(Entity e) {
		if(SpatialPool.playingSounds.containsKey(e.getId()))
			AppFacade.getMainSceneNode().detachChild(SpatialPool.playingSounds.get(e.getId()));
		
		AudioSource source = e.get(AudioSource.class);
		AudioNode node = getPrototype(source.getPath());
		
		if(node != null){
			node = node.clone();
			Naming n = entityData.getComponent(e.getId(), Naming.class);
			if(n != null)
				node.setName(n.getName());
			else
				node.setName("unnamed entity #"+e.getId());
			node.setLooping(source.isLoop());
			node.setVolume((float)source.getVolume().getValue());
			node.setPositional(false);
			
			node.setRefDistance(4);
			node.setReverbEnabled(false);
			node.play();
			
			SpatialPool.playingSounds.put(e.getId(), node);
			AppFacade.getMainSceneNode().attachChild(node);
		}
	}
	
	@Override
	protected void onEntityRemoved(Entity e) {
		if(SpatialPool.playingSounds.keySet().contains(e.getId())){
			SpatialPool.playingSounds.get(e.getId()).stop();
			AppFacade.getMainSceneNode().detachChild(SpatialPool.playingSounds.get(e.getId()));
		}
	}
	
	protected AudioNode getPrototype(String soundPath) {
		if(soundPath.isEmpty())
			return null;

		if (!soundPrototypes.containsKey(soundPath)) {
			try {
				AudioNode audio = new AudioNode(AppFacade.getAssetManager(), "sounds/" + soundPath);
				soundPrototypes.put(soundPath, audio);
			} catch (Exception e) {
				LogUtil.warning("Sound not found : sounds/" + soundPath + " ("+ e + ")");
				return null;
			}
		}
		return soundPrototypes.get(soundPath).clone();
	}
}
