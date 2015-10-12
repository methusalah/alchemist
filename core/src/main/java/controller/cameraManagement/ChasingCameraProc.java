package controller.cameraManagement;

import util.geometry.geom2d.Point2D;
import view.math.TranslateUtil;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.simsilica.es.Entity;

import model.ES.component.camera.ChasingCamera;
import model.ES.component.motion.MotionCapacity;
import model.ES.component.motion.PlanarStance;
import model.ES.component.relation.Parenting;
import controller.CameraManager;
import controller.entityAppState.Processor;

public class ChasingCameraProc extends Processor {
	
	private final Camera cam;

	public ChasingCameraProc(Camera cam) {
		this.cam = cam;
	}
	
	@Override
	protected void registerSets() {
		register(PlanarStance.class, ChasingCamera.class, Parenting.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime){
		manage(e, elapsedTime);
	}

	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime){
		manage(e, elapsedTime);
	}
	
	private void manage(Entity e, float elapsedTime){
		PlanarStance stance = e.get(PlanarStance.class);
		ChasingCamera chasing = e.get(ChasingCamera.class);
		Parenting parenting = e.get(Parenting.class);
		PlanarStance targetStance = entityData.getComponent(parenting.parent, PlanarStance.class);
		
		Point2D toTarget = targetStance.coord.getSubtraction(stance.coord);
		
		double minBrakingDistance = (chasing.getSpeed()*chasing.getSpeed())/(chasing.getDeceleration()*2);

		double newSpeed;
		if(minBrakingDistance >= toTarget.getLength())
			// deceleration
			newSpeed = Math.max(0, chasing.getSpeed()-chasing.getDeceleration()*elapsedTime);
		else
			// acceleration
			newSpeed = Math.min(chasing.getMaxSpeed(), chasing.getSpeed()+chasing.getAcceleration()*elapsedTime);
			
		toTarget = toTarget.getScaled(newSpeed*elapsedTime);
		
		Point2D newCoord = stance.coord.getAddition(toTarget);
		
		
		setComp(e, new ChasingCamera(chasing.getMaxSpeed(), newSpeed, chasing.getAcceleration(), chasing.getDeceleration()));
		setComp(e, new PlanarStance(newCoord, stance.orientation, stance.elevation, stance.upVector));
		
		cam.setLocation(TranslateUtil.toVector3f(newCoord.get3D(stance.elevation)));
		cam.lookAt(TranslateUtil.toVector3f(targetStance.coord.get3D(targetStance.elevation)), Vector3f.UNIT_Y);
		
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + System.lineSeparator());
		sb.append("    camera pos : "+ newCoord.get3D(stance.elevation) + System.lineSeparator());
		sb.append("    speed : " + newSpeed + System.lineSeparator());
		sb.append("    accelerate : " + (minBrakingDistance >= toTarget.getLength()) + System.lineSeparator());
	}

}
