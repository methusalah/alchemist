/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import util.geometry.geom2d.Point2D;
import app.AppFacade;

import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.renderer.Camera;

/**
 *
 * @author Benoît
 */
public abstract class CameraManager implements AnalogListener, ActionListener{
    protected String[] mappings;

    public Point2D getCamCorner(){
    	return new Point2D(AppFacade.getCamera().getWidth(), AppFacade.getCamera().getHeight());
    }
    
    public abstract void unregisterInputs(InputManager inputManager);
    public abstract void registerInputs(InputManager inputManager);
    public abstract void activate();
    public abstract void desactivate();
    
}
