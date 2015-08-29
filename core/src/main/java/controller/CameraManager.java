/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import view.math.TranslateUtil;
import app.AppFacade;

import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.Vector3f;

/**
 *
 * @author Benoît
 */
public abstract class CameraManager implements AnalogListener, ActionListener{
    protected String[] mappings;
	protected Point3D pos;
	protected Point3D target;

    public Point2D getCamCorner(){
    	return new Point2D(AppFacade.getCamera().getWidth(), AppFacade.getCamera().getHeight());
    }
    
    public abstract void unregisterInputs(InputManager inputManager);
    public abstract void registerInputs(InputManager inputManager);
    public abstract void activate();
    public abstract void desactivate();

	public void setLocation(Point3D pos) {
		this.pos = pos;
		placeCam();
	}

	public void translate(Point3D pos) {
		this.pos = this.pos.getAddition(pos);
		placeCam();
	}

	public void lookAt(Point3D targetPos) {
		this.target = targetPos;
		placeCam();
	}

	protected void zoom(double value) {
		pos = pos.getAddition(0, 0, -value);
		placeCam();
	}

	public void placeCam() {
		AppFacade.getCamera().setLocation(TranslateUtil.toVector3f(pos));
		AppFacade.getCamera().lookAt(TranslateUtil.toVector3f(target), Vector3f.UNIT_Y);
	}
    
}
