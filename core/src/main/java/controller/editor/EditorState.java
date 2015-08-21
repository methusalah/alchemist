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
import view.View;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;

import controller.AppState;
import controller.SpatialSelector;
import controller.cameraManagement.TopdownCameraManager;

/**
 *
 * @author Benoît
 */
public class EditorState extends AppState {
	EditorGUIController guiController;

	@Inject
	public EditorState(EditorView v, Camera cam, InputManager im, EditorGUIController eguic) {
		super(v, new EditorInputInterpreter(v), new SpatialSelector(cam, v, im),new TopdownCameraManager(cam, 10), im);
		spatialSelector.centered = false;
		guiController = eguic;
	}

	@Override
	public void update(float elapsedTime) {
		view.update(elapsedTime);
		guiController.update();
		
		ToolManager.setPointedSpatialLabel(spatialSelector.getSpatialLabel());
		ToolManager.setPointedSpatialEntityId(spatialSelector.getEntityId());
		Point2D coord = spatialSelector.getCoord(view.getRootNode());
		if (coord != null &&
				ModelManager.battlefieldReady &&
				ModelManager.getBattlefield().getMap().isInBounds(coord)) {
			ToolManager.updatePencilsPos(coord);
//			view.editorRend.drawPencil();
		}
	}

	@Override
	public void stateAttached(AppStateManager stateManager) {
		super.stateAttached(stateManager);
		inputManager.setCursorVisible(true);
		guiController.activate();
	}

	@Override
	public void stateDetached(AppStateManager stateManager) {
		super.stateDetached(stateManager);
	}

}
