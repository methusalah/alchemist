package logic.processor.rendering.audio;

import java.util.HashMap;
import java.util.Map;

import com.brainless.alchemist.model.ECS.builtInComponent.Naming;
import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.brainless.alchemist.model.tempImport.RendererPlatform;
import com.jme3.audio.AudioNode;
import com.simsilica.es.Entity;

import component.assets.AudioSource;
import logic.processor.Pool;
import util.LogUtil;

public class AudioSourceProc extends BaseProcessor {
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
		if(Pool.playingSounds.containsKey(e.getId()))
			RendererPlatform.getMainSceneNode().detachChild(Pool.playingSounds.get(e.getId()));
		
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
			
			Pool.playingSounds.put(e.getId(), node);
			RendererPlatform.getMainSceneNode().attachChild(node);
		}
	}
	
	@Override
	protected void onEntityRemoved(Entity e) {
		if(Pool.playingSounds.keySet().contains(e.getId())){
			Pool.playingSounds.get(e.getId()).stop();
			RendererPlatform.getMainSceneNode().detachChild(Pool.playingSounds.get(e.getId()));
		}
	}
	
	protected AudioNode getPrototype(String soundPath) {
		if(soundPath.isEmpty())
			return null;

		if (!soundPrototypes.containsKey(soundPath)) {
			try {
				AudioNode audio = new AudioNode(RendererPlatform.getAssetManager(), "sounds/" + soundPath);
				soundPrototypes.put(soundPath, audio);
			} catch (Exception e) {
				LogUtil.warning("Sound not found : sounds/" + soundPath + " ("+ e + ")");
				return null;
			}
		}
		return soundPrototypes.get(soundPath).clone();
	}
}
