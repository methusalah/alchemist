package view.drawingProcessors.audio;

import model.ES.component.audio.AudioSource;
import model.ES.component.motion.PlanarStance;
import model.ES.component.visuals.Model;
import util.LogUtil;
import util.geometry.geom3d.Point3D;
import util.math.AngleUtil;
import view.SpatialPool;
import view.math.TranslateUtil;

import com.jme3.audio.AudioNode;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.simsilica.es.Entity;

import controller.ECS.Processor;

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
