/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package controller;

import app.AppFacade;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.NiftyControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * @author Benoît
 */
public abstract class GUIController implements ScreenController {
	protected GUIDrawer drawer;

	protected boolean redrawAsked = false;

	public GUIController() {
		AppFacade.getNifty().registerScreenController(this);
	}

	public abstract void update();

	public abstract void activate();

	protected Element getElement(String s) {
		return AppFacade.getNifty().getCurrentScreen().findElementByName(s);
	}

	protected <T extends NiftyControl> T getControl(String id, Class<T> controlClass) {
		return AppFacade.getNifty().getCurrentScreen().findNiftyControl(id, controlClass);
	}

	public void askRedraw() {
		redrawAsked = true;
	}

}
