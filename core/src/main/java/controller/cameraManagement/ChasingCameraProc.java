package controller.cameraManagement;

import util.geometry.geom2d.Point2D;

import com.simsilica.es.Entity;

import model.ES.component.camera.ChasingCamera;
import model.ES.component.motion.MotionCapacity;
import model.ES.component.motion.PlanarStance;
import model.ES.component.relation.Parenting;
import controller.CameraManager;
import controller.entityAppState.Processor;

public class ChasingCameraProc extends Processor {
	
	private final CameraManager camManager;

	public ChasingCameraProc(CameraManager camManager) {
		this.camManager = camManager;
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
		ChasingCamera cam = e.get(ChasingCamera.class);
		Parenting parenting = e.get(Parenting.class);
		PlanarStance targetStance = entityData.getComponent(parenting.parent, PlanarStance.class);
		
		Point2D toTarget = targetStance.coord.getSubtraction(stance.coord);
		
		double minBrakingDistance = (cam.speed*cam.speed)/(cam.deceleration*2);

		double newSpeed;
		if(minBrakingDistance >= toTarget.getLength())
			// deceleration
			newSpeed = Math.max(0, cam.speed-cam.deceleration*elapsedTime);
		else
			// acceleration
			newSpeed = Math.min(cam.maxSpeed, cam.speed+cam.acceleration*elapsedTime);
			
		toTarget = toTarget.getScaled(newSpeed*elapsedTime);
		
		Point2D newCoord = stance.coord.getAddition(toTarget);
		
		
		setComp(e, new ChasingCamera(cam.maxSpeed, newSpeed, cam.acceleration, cam.deceleration));
		setComp(e, new PlanarStance(newCoord, stance.orientation, stance.elevation, stance.upVector));
		
		camManager.setLocation(newCoord.get3D(stance.elevation));
		camManager.lookAt(targetStance.coord.get3D(targetStance.elevation));
		
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + System.lineSeparator());
		sb.append("    camera pos : "+ newCoord.get3D(stance.elevation) + System.lineSeparator());
		sb.append("    speed : " + newSpeed + System.lineSeparator());
		sb.append("    accelerate : " + (minBrakingDistance >= toTarget.getLength()) + System.lineSeparator());
		app.getDebugger().add(sb.toString());

		
	}

}
