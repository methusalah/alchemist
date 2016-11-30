package com.brainless.alchemist.model.state;

import com.brainless.alchemist.model.tempImport.TranslateUtil;
import com.jme3.app.state.AbstractAppState;
import com.jme3.collision.MotionAllowedListener;
import com.jme3.input.InputManager;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

import util.LogUtil;
import util.geometry.geom2d.Point2D;

/**
 * App state for camera control.
 * 
 * @author benoit
 *
 */
public class DraggableCameraState extends AbstractAppState {
	private static String[] mappings = new String[] { "FLYCAM_Left", "FLYCAM_Right", "FLYCAM_Up", "FLYCAM_Down",

			"FLYCAM_StrafeLeft", "FLYCAM_StrafeRight", "FLYCAM_Forward", "FLYCAM_Backward",

			"FLYCAM_Rise", "FLYCAM_Lower" };

	protected Camera cam;
	protected Vector3f initialUpVec;
	protected float rotationSpeed = 0.01f;
	protected float moveSpeed = 3f;
	protected MotionAllowedListener motionAllowed = null;
	protected boolean enabled = true;
	protected InputManager inputManager;
	
	private Point2D velocity = Point2D.ORIGIN;

	public DraggableCameraState(Camera cam) {
		this.cam = cam;
		initialUpVec = cam.getUp().clone();
	}

	/**
	 * Set the top direction of the camera, typically 0, 0, 1
	 * @param upVec
	 */
	public void setUpVector(Vector3f upVec) {
		initialUpVec.set(upVec);
	}

	public void setMotionAllowedListener(MotionAllowedListener listener) {
		this.motionAllowed = listener;
	}

	/**
	 * Set the speed of the camera in world units per second.
	 * @param moveSpeed
	 */
	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	/**
	 * Get the speed of the camera in world units per second.
	 * @return
	 */
	public float getMoveSpeed() {
		return moveSpeed;
	}

	/**
	 * Set the rotation speed of the camera in radians per second.
	 * @param moveSpeed
	 */
	public void setRotationSpeed(float rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	/**
	 * Get the rotation speed of the camera in radians per second.
	 * @return
	 */
	public float getRotationSpeed() {
		return rotationSpeed;
	}

	public void rotateCamera(float value, Vector3f axis) {
		Matrix3f mat = new Matrix3f();
		mat.fromAngleNormalAxis(rotationSpeed * value, axis);

		Vector3f up = cam.getUp();
		Vector3f left = cam.getLeft();
		Vector3f dir = cam.getDirection();

		mat.mult(up, up);
		mat.mult(left, left);
		mat.mult(dir, dir);

		Quaternion q = new Quaternion();
		q.fromAxes(left, up, dir);
		q.normalizeLocal();

		cam.setAxes(q);
	}

	public void riseCamera(float value) {
		Vector3f vel = new Vector3f(0, value * moveSpeed, 0);
		Vector3f pos = cam.getLocation().clone();

		if (motionAllowed != null)
			motionAllowed.checkMotionAllowed(pos, vel);
		else
			pos.addLocal(vel);

		cam.setLocation(pos);
	}

	public void strafeCamera(float value, boolean leftRight) {
		Vector3f vel = new Vector3f();
		Vector3f pos = cam.getLocation().clone();

		if(leftRight) {
			cam.getLeft(vel);
		} else {
			cam.getUp(vel);
		}
		vel.multLocal(value * moveSpeed);

		if (motionAllowed != null)
			motionAllowed.checkMotionAllowed(pos, vel);
		else
			pos.addLocal(vel);

		cam.setLocation(pos);
		moveSpeed = Math.max(1, cam.getLocation().z/20);
	}

	public void moveCamera(float value, boolean sideways) {
		Vector3f vel = new Vector3f();
		Vector3f pos = cam.getLocation().clone();

		if (sideways) {
			cam.getLeft(vel);
		} else {
			cam.getDirection(vel);
		}
		vel.multLocal(value * moveSpeed);

		if (motionAllowed != null)
			motionAllowed.checkMotionAllowed(pos, vel);
		else
			pos.addLocal(vel);

		cam.setLocation(pos);
		moveSpeed = Math.max(1, cam.getLocation().z/20);
	}
	
	public void setVelocity(Point2D v){
		velocity = v;
	}
	
	@Override
	public void update(float tpf) {
		if(!velocity.isOrigin()){
			cam.setLocation(cam.getLocation().add(TranslateUtil.toVector3f(velocity.getScaled(moveSpeed/2))));
		}
	}
}
