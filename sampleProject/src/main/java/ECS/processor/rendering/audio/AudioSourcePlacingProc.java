package ECS.processor.rendering.audio;

import com.jme3.audio.AudioNode;
import com.simsilica.es.Entity;

import ECS.component.assets.AudioSource;
import ECS.component.motion.PlanarStance;
import ECS.processor.SpatialPool;
import model.ECS.pipeline.Processor;
import model.tempImport.TranslateUtil;

public class AudioSourcePlacingProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(AudioSource.class, PlanarStance.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		AudioNode node = SpatialPool.playingSounds.get(e.getId());
		node.setPositional(true);
		node.play();
		onEntityUpdated(e);
	}
	@Override
	protected void onEntityUpdated(Entity e) {
		AudioNode node = SpatialPool.playingSounds.get(e.getId());

		if(node == null)
			return;
		
		PlanarStance stance = e.get(PlanarStance.class);

		// translation
		node.setLocalTranslation(TranslateUtil.toVector3f(stance.getCoord().get3D(stance.getElevation())));
	}
}
