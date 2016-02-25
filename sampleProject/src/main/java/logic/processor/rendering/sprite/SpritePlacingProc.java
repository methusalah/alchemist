package logic.processor.rendering.sprite;

import com.brainless.alchemist.model.ECS.pipeline.Processor;
import com.brainless.alchemist.model.tempImport.TranslateUtil;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.simsilica.es.Entity;

import component.assets.Sprite;
import component.motion.PlanarStance;
import logic.processor.SpatialPool;
import util.geometry.geom3d.Point3D;

public class SpritePlacingProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(Sprite.class, PlanarStance.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		Spatial s = SpatialPool.models.get(e.getId());
		if(s == null)
			return;
		
		PlanarStance stance = e.get(PlanarStance.class);

		// translation
		s.setLocalTranslation(TranslateUtil.toVector3f(stance.coord.get3D(stance.elevation)));
		// rotation
		Quaternion r = new Quaternion();
		Point3D pu = stance.upVector;
		Point3D pv = Point3D.UNIT_X.getRotationAroundZ(stance.orientation.getValue());
		Vector3f u = TranslateUtil.toVector3f(pu).normalize();
		Vector3f v = TranslateUtil.toVector3f(pv).normalize();
		r.lookAt(v, u);
		double angle = Math.acos(pu.getDotProduct(pv) / (pu.getNorm() * pv.getNorm()));
		r = r.mult(new Quaternion().fromAngles((float) (-angle), 0, 0));

		s.setLocalRotation(r);
	}
}
