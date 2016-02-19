package controller;

import app.AppFacade;

import com.jme3.input.InputManager;


public abstract class Mapping {
	protected String[] mapping;

	protected abstract void register();

	protected void unregister(){
		for (String s : mapping) {
			if (AppFacade.getInputManager().hasMapping(s)) {
				AppFacade.getInputManager().deleteMapping(s);
			}
		}
	}

	public String[] getMapping() {
		return mapping;
	}

}
