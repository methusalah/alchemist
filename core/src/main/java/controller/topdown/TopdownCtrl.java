package controller.topdown;

import com.jme3.app.state.AppStateManager;

import controller.Controller;
import controller.cameraManagement.TopdownCameraManager;
import model.tempImport.DataState;
import view.TopdownView;

/**
 *
 */
public class TopdownCtrl extends Controller {

	public TopdownCtrl() {
		spatialSelector.centered = false;

		inputInterpreter = new TopdownInputInterpreter(getView());
		cameraManager = new TopdownCameraManager(10);
	}
	
	@Override
	public void onInitialize(AppStateManager stateManager) {
		((TopdownInputInterpreter)inputInterpreter).setCommand(stateManager.getState(DataState.class).getCommand());
	}
	
	private TopdownView getView(){
		return (TopdownView)view;
	}
}
