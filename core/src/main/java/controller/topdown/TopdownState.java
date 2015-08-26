package controller.topdown;

import model.ModelManager;
import model.battlefield.army.ArmyManager;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import view.TopdownView;
import view.View;
import app.CosmoVania;

import com.google.inject.Inject;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;

import controller.AppState;
import controller.SpatialSelector;
import controller.cameraManagement.TopdownCameraManager;

/**
 *
 */
public class TopdownState extends AppState {
	private boolean paused = false;

	protected CosmoVania app;
	
	@Inject
	public TopdownState(TopdownView v, Camera cam, InputManager im) {
		super(v, new TopdownInputInterpreter(v), new SpatialSelector(cam, v, im),new TopdownCameraManager(cam, 10), im);
		spatialSelector.centered = false;
	}

	@Override
	public final void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		this.app = (CosmoVania)app;
		((TopdownInputInterpreter)inputInterpreter).app = this.app;
	}

	@Override
	public void update(float elapsedTime) {
		// update army
		if(!paused) {
			ArmyManager.update(elapsedTime);
		}
		
		ModelManager.command.target = spatialSelector.getCoord(view.getPlane());
	}

	@Override
	public void stateAttached(AppStateManager stateManager) {
		super.stateAttached(stateManager);
		inputManager.setCursorVisible(true);
	}
}
