/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import util.annotation.CameraRef;
import view.View;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;

import controller.cameraManagement.CameraManager;

/**
 *
 * @author Benoît
 */
public abstract class AppState extends AbstractAppState {

	protected final InputInterpreter inputInterpreter;
	protected final SpatialSelector spatialSelector;
	protected final CameraManager cameraManager;
	protected final View view;

	@Inject
	public AppState(View view, InputInterpreter inputInterpreter, SpatialSelector spatialSelector, CameraManager cameraManager) {
		super();
		this.view = view;
		this.inputInterpreter = inputInterpreter;
		this.spatialSelector = spatialSelector;
		this.cameraManager = cameraManager;
	}

	@Override
	public void stateDetached(AppStateManager stateManager) {
//		inputInterpreter.unregisterInputs(inputManager);
//		cameraManager.unregisterInputs(inputManager);
	}

	@Override
	public void stateAttached(AppStateManager stateManager) {
//		inputInterpreter.registerInputs(inputManager);
//		cameraManager.registerInputs(inputManager);
//		cameraManager.activate();
	}
}
