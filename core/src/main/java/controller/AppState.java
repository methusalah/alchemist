/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import util.LogUtil;
import view.View;

import com.google.inject.Inject;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;

import controller.cameraManagement.CameraManager;

/**
 *
 * @author Benoît
 */
public abstract class AppState extends AbstractAppState {

	protected final InputInterpreter inputInterpreter;
	protected final SpatialSelector spatialSelector;
	protected final CameraManager cameraManager;
	protected final InputManager inputManager; 
	protected final View view;

	@Inject
	public AppState(View view, InputInterpreter ii, SpatialSelector ss, CameraManager cm, InputManager im) {
		super();
		this.view = view;
		this.inputInterpreter = ii;
		this.spatialSelector = ss;
		this.cameraManager = cm;
		this.inputManager = im;
		
	}

	@Override
	public void stateDetached(AppStateManager stateManager) {
		inputInterpreter.unregisterInputs(inputManager);
		cameraManager.unregisterInputs(inputManager);
	}

	@Override
	public void stateAttached(AppStateManager stateManager) {
		inputInterpreter.registerInputs(inputManager);
		cameraManager.registerInputs(inputManager);
		cameraManager.activate();
	}
}
