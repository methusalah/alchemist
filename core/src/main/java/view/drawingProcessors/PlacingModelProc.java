package view.drawingProcessors;

import model.ES.component.ModelComp;
import model.ES.component.motion.PlanarStance;
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
		register(ModelComp.class, PlanarStance.class);
	}
	
	
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		ModelComp model = e.get(ModelComp.class);
		if(!model.isCreated())
			return;
		
		PlanarStance position = e.get(PlanarStance.class);
		Spatial s = SpatialPool.models.get(e.getId());

		// translation
		s.setLocalTranslation(TranslateUtil.toVector3f(position.getCoord()));

		// rotation
		Quaternion r = new Quaternion();
		Point3D pu = position.getUpVector();
		Point3D pv = position.getPlanarVector();
		Vector3f u = TranslateUtil.toVector3f(pu).normalize();
		Vector3f v = TranslateUtil.toVector3f(pv).normalize();
		r.lookAt(v, u);

		// we correct the pitch of the unit because the direction is always flatten
		// this is only to follow the terrain relief
		double angle = Math.acos(pu.getDotProduct(pv) / (pu.getNorm() * pv.getNorm()));
		r = r.mult(new Quaternion().fromAngles((float) (-angle+AngleUtil.RIGHT+model.getPitchFix()), (float) (model.getRollFix()), (float) (model.getYawFix())));

		s.setLocalRotation(r);
	}
	
}
