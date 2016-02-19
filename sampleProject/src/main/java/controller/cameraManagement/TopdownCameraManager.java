package controller.cameraManagement;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;

import controller.CameraManager;
import util.geometry.geom3d.Point3D;

public class TopdownCameraManager extends CameraManager {
	protected final static String STRAFE_NORTH = "strafenorth";
	protected final static String STRAFE_SOUTH = "strafesouth";
	protected final static String STRAFE_EAST = "strafeeast";
	protected final static String STRAFE_WEST = "strafewest";
	protected final static String ZOOM_IN = "zoomin";
	protected final static String ZOOM_OUT = "zoomout";

	public TopdownCameraManager(float elevation) {
		pos = new Point3D(0, 0, elevation);
		target = new Point3D(0, 0, 0);
		placeCam();
		setMappings();
	}

	private void setMappings(){
		mappings = new String[]{
				STRAFE_NORTH,
				STRAFE_SOUTH,
				STRAFE_EAST,
				STRAFE_WEST,
				ZOOM_IN,
				ZOOM_OUT,
		};
	}

	@Override
	public void activate() {
		placeCam();
	}

	@Override
	public void desactivate() {
	}

	@Override
	public void unregisterInputs(InputManager inputManager){
		for(String s : mappings) {
			if(inputManager.hasMapping(s)) {
				inputManager.deleteMapping(s);
			}
		}
		inputManager.removeListener(this);
	}

	@Override
	public void registerInputs(InputManager inputManager){
		inputManager.addMapping(STRAFE_NORTH, new KeyTrigger(KeyInput.KEY_UP));
		inputManager.addMapping(STRAFE_SOUTH, new KeyTrigger(KeyInput.KEY_DOWN));
		inputManager.addMapping(STRAFE_EAST, new KeyTrigger(KeyInput.KEY_RIGHT));
		inputManager.addMapping(STRAFE_WEST, new KeyTrigger(KeyInput.KEY_LEFT));
		inputManager.addMapping(ZOOM_IN, new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
		inputManager.addMapping(ZOOM_OUT, new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));
		inputManager.addListener(this, mappings);
	}

	@Override
	public void onAnalog(String name, float value, float tpf) {
		double velocity = tpf*5;
		switch(name){
			case STRAFE_NORTH :
				translate(new Point3D(0, velocity, 0));
				lookAt(pos.get2D().get3D(0));
				break;
			case STRAFE_SOUTH :
				translate(new Point3D(0, -velocity, 0));
				lookAt(pos.get2D().get3D(0));
				break;
			case STRAFE_EAST :
				translate(new Point3D(velocity, 0, 0));
				lookAt(pos.get2D().get3D(0));
				break;
			case STRAFE_WEST :
				translate(new Point3D(-velocity, 0, 0));
				lookAt(pos.get2D().get3D(0));
				break;
			case ZOOM_IN : zoom(0.3); break;
			case ZOOM_OUT : zoom(-0.3); break;
		}
	}

	@Override
	public void onAction(String name, boolean isPressed, float tpf) {

	}
}
