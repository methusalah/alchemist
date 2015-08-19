package controller;

import view.View;

import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;

public abstract class InputInterpreter implements AnalogListener, ActionListener {
	protected final View view;
	
	protected String[] mappings;

	protected InputInterpreter(View view) {
		this.view = view;
	}

	protected abstract void registerInputs(InputManager inputManager);

	protected abstract void unregisterInputs(InputManager inputManager);
}
