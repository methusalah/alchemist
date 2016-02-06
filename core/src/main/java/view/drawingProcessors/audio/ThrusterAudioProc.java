package view.drawingProcessors.audio;

import java.util.HashMap;
import java.util.Map;

import com.jme3.audio.AudioNode;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import app.AppFacade;
import controller.ECS.Processor;
import model.ES.commonLogic.Controlling;
import model.ES.component.Parenting;
import model.ES.component.assets.ThrusterAudioSource;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.Thruster;
import model.ES.component.motion.ThrusterControl;
import util.LogUtil;
import view.SpatialPool;

public class ThrusterAudioProc extends Processor {
	Map<String, AudioNode> sounds = new HashMap<>();

	@Override
	protected void registerSets() {
		registerDefault(ThrusterAudioSource.class, PlanarStance.class, ThrusterControl.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		ThrusterAudioSource source = e.get(ThrusterAudioSource.class);
		
		boolean playing = source.getStartTime() != 0;
		Thruster t = Controlling.getControl(Thruster.class, e.getId(), entityData);
		if(t == null)
			return;
		
		if(playing){
			if(t.activation.getValue() > 0){
				play(e, source);
			} else {
				stop(e, source);
			}
		} else {
			if(t.activation.getValue() > 0){
				start(e, source);
			}
		}
	}
	
	private void play(Entity e, ThrusterAudioSource source){
		if(source.getLoopTime() != 0 && source.getLoopTime() < System.currentTimeMillis()){
			source.setLoopTime(0);
			AudioNode audio = getAudioNode(source.getLoopPath());
			
			audio.setLooping(true);
			audio.setVolume((float)source.getVolume().getValue());
			audio.setPositional(false);
			
			audio.setRefDistance(4);
			audio.setReverbEnabled(false);
			playSound(e, audio);
		}
	}
	
	private void start(Entity e, ThrusterAudioSource source){
		long t = System.currentTimeMillis();
		source.setStartTime(t);
		AudioNode audio = getAudioNode(source.getStartPath());
		source.setLoopTime(t + (long)(audio.getAudioData().getDuration()*1000));
		
		audio.setLooping(false);
		audio.setVolume((float)source.getVolume().getValue());
		audio.setPositional(false);
		
		audio.setRefDistance(4);
		audio.setReverbEnabled(false);
		playSound(e, audio);
	}

	private void stop(Entity e, ThrusterAudioSource source){
		source.setStartTime(0);
		AudioNode audio = getAudioNode(source.getEndPath());
		
		audio.setLooping(false);
		audio.setVolume((float)source.getVolume().getValue());
		audio.setPositional(false);
		
		audio.setRefDistance(4);
		audio.setReverbEnabled(false);
		playSound(e, audio);
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
