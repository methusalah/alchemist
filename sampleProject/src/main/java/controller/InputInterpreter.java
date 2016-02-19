package controller;

import view.View;
import app.AppFacade;

import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;

public abstract class InputInterpreter implements AnalogListener, ActionListener {
	protected Mapping mapping;
	protected View view;

	public InputInterpreter(View view, Mapping mapping) {
		this.view = view;
		this.mapping = mapping;
	}
	
	protected void registerInputs() {
		mapping.register();
		AppFacade.getInputManager().addListener(this, mapping.getMapping());
	}
	protected void unregisterInputs() {
		mapping.unregister();
		AppFacade.getInputManager().removeListener(this);
	}
	
	@Override
	public final void onAction(String name, boolean isPressed, float tpf) {
		if(isPressed)
			onActionPressed(name, tpf);
		else
			onActionReleased(name, tpf);
	}
	
	protected abstract void onActionPressed(String name, float tpf);
	protected abstract void onActionReleased(String name, float tpf);

}
