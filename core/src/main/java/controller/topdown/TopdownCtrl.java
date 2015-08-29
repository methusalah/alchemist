package controller.topdown;

import model.ModelManager;
import model.battlefield.army.ArmyManager;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import view.EditorView;
import view.TopdownView;
import view.View;
import app.AppFacade;
import app.CosmoVania;

import com.google.inject.Inject;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;

import controller.Controller;
import controller.SpatialSelector;
import controller.cameraManagement.TopdownCameraManager;
import controller.editor.EditorInputInterpreter;

/**
 *
 */
public class TopdownCtrl extends Controller {
	public TopdownCtrl() {
		spatialSelector.centered = false;

		view = new TopdownView();
		inputInterpreter = new TopdownInputInterpreter(getView());
		cameraManager = new TopdownCameraManager(10);
	}
	
	@Override
	public void update(float elapsedTime) {
		ModelManager.command.target = spatialSelector.getCoord(view.getPlane());
		getView().actordrawer.render();
	}

	@Override
	public void stateAttached(AppStateManager stateManager) {
		super.stateAttached(stateManager);
		AppFacade.getInputManager().setCursorVisible(true);
	}
	
	private TopdownView getView(){
		return (TopdownView)view;
	}
}
