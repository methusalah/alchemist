package controller.cameraManagement;

import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import view.math.TranslateUtil;
import app.AppFacade;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.simsilica.es.Entity;

import model.ES.component.camera.ChasingCamera;
import model.ES.component.motion.MotionCapacity;
import model.ES.component.motion.PlanarStance;
import model.ES.component.relation.Parenting;
import controller.CameraManager;
import controller.ECS.LogicThread;
import controller.ECS.Processor;

public class ChasingCameraProc extends Processor {
	
	@Override
	protected void registerSets() {
		registerDefault(PlanarStance.class, ChasingCamera.class, Parenting.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		PlanarStance stance = e.get(PlanarStance.class);
		ChasingCamera chasing = e.get(ChasingCamera.class);
		Parenting parenting = e.get(Parenting.class);
		PlanarStance targetStance = entityData.getComponent(parenting.getParent(), PlanarStance.class);
		
		if(targetStance == null)
			return;
		
		Point2D toTarget = targetStance.coord.getSubtraction(stance.coord);
		
		double minBrakingDistance = (chasing.getSpeed()*chasing.getSpeed())/(chasing.getDeceleration()*2);

		double newSpeed;
		if(minBrakingDistance >= toTarget.getLength())
			// deceleration
			newSpeed = Math.max(0, chasing.getSpeed()-chasing.getDeceleration()*LogicThread.TIME_PER_FRAME);
		else
			// acceleration
			newSpeed = Math.min(chasing.getMaxSpeed(), chasing.getSpeed()+chasing.getAcceleration()*LogicThread.TIME_PER_FRAME);
			
		toTarget = toTarget.getScaled(newSpeed*LogicThread.TIME_PER_FRAME);
		
		Point2D newCoord = stance.coord.getAddition(toTarget);
		Point3D pos = newCoord.get3D(stance.elevation);
		Point3D target = targetStance.coord.get3D(targetStance.elevation);
		
		setComp(e, new ChasingCamera(chasing.getMaxSpeed(), newSpeed, chasing.getAcceleration(), chasing.getDeceleration(), pos, target));
		setComp(e, new PlanarStance(newCoord, stance.orientation, stance.elevation, stance.upVector));
	}

}
