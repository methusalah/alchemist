package controller.topdown;

import model.ModelManager;
import view.TopdownView;
import app.AppFacade;

import com.jme3.app.state.AppStateManager;

import controller.Controller;
import controller.cameraManagement.TopdownCameraManager;

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
	}

	@Override
	public void stateAttached(AppStateManager stateManager) {
		super.stateAttached(stateManager);
		//AppFacade.getInputManager().setCursorVisible(true);
	}
	
	private TopdownView getView(){
		return (TopdownView)view;
	}
}
