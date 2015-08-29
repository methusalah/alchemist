/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.editor;

import model.ModelManager;
import model.editor.ToolManager;
import util.event.MapResetEvent;
import util.event.EventManager;
import util.geometry.geom2d.Point2D;
import view.EditorView;
import view.TopdownView;
import view.View;
import app.AppFacade;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;

import controller.Controller;
import controller.SpatialSelector;
import controller.cameraManagement.ChasingCameraProc;
import controller.cameraManagement.TopdownCameraManager;

/**
 *
 * @author Benoît
 */
public class EditorCtrl extends Controller {
	EditorGUIController guiController;

	@Inject
	public EditorCtrl() {
		spatialSelector.centered = false;
		guiController = new EditorGUIController();
		view = new EditorView();
		inputInterpreter = new EditorInputInterpreter(getView());
		cameraManager = new TopdownCameraManager(10);
	}

	@Override
	public void update(float elapsedTime) {
		view.update(elapsedTime);
		guiController.update();
		
		Point2D coord = spatialSelector.getCoord(AppFacade.getRootNode());
		if (coord != null &&
				ModelManager.battlefieldReady &&
				ModelManager.getBattlefield().getMap().isInBounds(coord)) {
			ToolManager.updatePencilsPos(coord);
//			view.editorRend.drawPencil();
		}
		getView().actordrawer.render();

	}

	@Override
	public void stateAttached(AppStateManager stateManager) {
		super.stateAttached(stateManager);
		AppFacade.getInputManager().setCursorVisible(true);
		guiController.activate();
	}

	@Override
	public void stateDetached(AppStateManager stateManager) {
		super.stateDetached(stateManager);
	}
	
	private EditorView getView(){
		return (EditorView)view;
	}
	
	@Override
	public void onEnabled() {
		ChasingCameraProc proc = AppFacade.getStateManager().getState(ChasingCameraProc.class);
		if(proc != null)
			proc.setEnabled(false);
	}

	@Override
	public void onDisabled() {
		ChasingCameraProc proc = AppFacade.getStateManager().getState(ChasingCameraProc.class);
		if(proc != null)
			proc.setEnabled(true);
	}

}
