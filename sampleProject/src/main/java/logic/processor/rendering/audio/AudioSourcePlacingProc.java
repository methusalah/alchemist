package logic.processor.rendering.audio;

import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.brainless.alchemist.model.tempImport.TranslateUtil;
import com.jme3.audio.AudioNode;
import com.simsilica.es.Entity;

import component.assets.AudioSource;
import component.motion.PlanarStance;
import logic.processor.Pool;

public class AudioSourcePlacingProc extends BaseProcessor {

	@Override
	protected void registerSets() {
		registerDefault(AudioSource.class, PlanarStance.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		AudioNode node = Pool.playingSounds.get(e.getId());
		node.setPositional(true);
		node.play();
		onEntityUpdated(e);
	}
	@Override
	protected void onEntityUpdated(Entity e) {
		AudioNode node = Pool.playingSounds.get(e.getId());

		if(node == null)
			return;
		
		PlanarStance stance = e.get(PlanarStance.class);

		// translation
		node.setLocalTranslation(TranslateUtil.toVector3f(stance.getCoord().get3D(stance.getElevation())));
	}
}
