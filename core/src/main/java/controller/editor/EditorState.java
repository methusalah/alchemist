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
	protected Point2D screenCoord;

	@Inject
	public EditorState(EditorView v, Camera cam, InputManager im) {
		super(v, new EditorInputInterpreter(v), new SpatialSelector(cam, v, im),new TopdownCameraManager(cam, 10), im);
		spatialSelector.centered = false;
	}

	@Override
	public void update(float elapsedTime) {
		view.update(elapsedTime);
	}

	@Override
	public void stateAttached(AppStateManager stateManager) {
		super.stateAttached(stateManager);
		inputManager.setCursorVisible(true);
	}

	@Override
	public void stateDetached(AppStateManager stateManager) {
		super.stateDetached(stateManager);
	}

}
