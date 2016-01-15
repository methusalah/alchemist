package view.drawingProcessors.audio;

import java.util.HashMap;
import java.util.Map;

import com.jme3.audio.AudioNode;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import app.AppFacade;
import controller.ECS.Processor;
import model.ES.commonLogic.Controlling;
import model.ES.component.assets.Ability;
import model.ES.component.assets.Thruster;
import model.ES.component.audio.AudioSource;
import model.ES.component.audio.ThrusterAudioSource;
import model.ES.component.hierarchy.AbilityControl;
import model.ES.component.hierarchy.Parenting;
import model.ES.component.hierarchy.ThrusterControl;
import model.ES.component.motion.PlanarStance;
import util.LogUtil;
import view.SpatialPool;

public class AbilityAudioProc extends Processor {
	Map<String, AudioNode> sounds = new HashMap<>();

	@Override
	protected void registerSets() {
		registerDefault(AudioSource.class, AbilityControl.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		AudioSource source = e.get(AudioSource.class);
		
		Ability a = Controlling.getControl(Ability.class, e.getId(), entityData);
		
		if(a.isTriggered())
			start(e, source);
		else if(source.isLoop())
			stop(e, source);
	}
	
	private void start(Entity e, AudioSource source){
		AudioNode audio = getAudioNode(source.getPath());
		audio.setLooping(source.isLoop());
		audio.setVolume((float)source.getVolume().getValue());
		audio.setPositional(false);
		
		audio.setRefDistance(4);
		audio.setReverbEnabled(false);
		playSound(e, audio);
	}

	private void stop(Entity e, AudioSource source){
		AudioNode audio = getAudioNode(source.getPath());
		audio.stop();
	}
	
	protected AudioNode getAudioNode(String soundPath) {
		if (!sounds.containsKey(soundPath)) {
			sounds.put(soundPath, new AudioNode(AppFacade.getAssetManager(), "sounds/" + soundPath));
			AppFacade.getMainSceneNode().attachChild(sounds.get(soundPath));
		}
		return sounds.get(soundPath).clone();
	}
	
	private void playSound(Entity e, AudioNode a){
		AudioNode current = SpatialPool.playingSounds.get(e.getId());
		if(current != null)
			current.stop();
		a.play();
		SpatialPool.playingSounds.put(e.getId(), a);
	}

}
