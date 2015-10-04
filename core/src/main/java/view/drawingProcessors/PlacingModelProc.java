package view.drawingProcessors;

import model.ES.component.motion.PlanarStance;
import model.ES.component.visuals.Model;
import util.geometry.geom3d.Point3D;
import util.math.AngleUtil;
import view.SpatialPool;
import view.math.TranslateUtil;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class PlacingModelProc extends Processor {

	@Override
	protected void registerSets() {
		register(Model.class, PlanarStance.class);
	}
	
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
		manage(e, elapsedTime);
	}
	
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		manage(e, elapsedTime);
	}
	
	private void manage(Entity e, float elapsedTime) {
		Model model = e.get(Model.class);
		if(!model.created)
			return;
		
		PlanarStance stance = e.get(PlanarStance.class);
		Spatial s = SpatialPool.models.get(e.getId());

		// translation
		s.setLocalTranslation(TranslateUtil.toVector3f(stance.coord.get3D(stance.elevation)));

		// rotation
		Quaternion r = new Quaternion();
		Point3D pu = stance.upVector;
		Point3D pv = Point3D.UNIT_X.getRotationAroundZ(stance.orientation);
		Vector3f u = TranslateUtil.toVector3f(pu).normalize();
		Vector3f v = TranslateUtil.toVector3f(pv).normalize();
		r.lookAt(v, u);

		// we correct the pitch of the unit because the direction is always flatten
		// this is only to follow the terrain relief
		double angle = Math.acos(pu.getDotProduct(pv) / (pu.getNorm() * pv.getNorm()));
		r = r.mult(new Quaternion().fromAngles((float) (-angle+AngleUtil.RIGHT+model.pitchFix), (float) (model.rollFix), (float) (model.yawFix)));

		s.setLocalRotation(r);
	}
}
