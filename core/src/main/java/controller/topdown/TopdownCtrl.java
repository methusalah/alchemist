package controller.topdown;

import model.Command;
import view.TopdownView;

import com.jme3.app.state.AppStateManager;

import controller.Controller;
import controller.ECS.DataState;
import controller.cameraManagement.TopdownCameraManager;

/**
 *
 */
public class TopdownCtrl extends Controller {

	private Command command; 
	
	@Override
	public void onInitialize(AppStateManager stateManager) {
		command = stateManager.getState(DataState.class).getCommand();
		((TopdownInputInterpreter)inputInterpreter).setCommand(command);
	}
	
	public TopdownCtrl() {
		spatialSelector.centered = false;

		view = new TopdownView();
		inputInterpreter = new TopdownInputInterpreter(getView());
		cameraManager = new TopdownCameraManager(10);
	}
	
	private TopdownView getView(){
		return (TopdownView)view;
	}
}
