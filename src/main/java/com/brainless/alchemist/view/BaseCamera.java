package com.brainless.alchemist.view;

import com.brainless.alchemist.model.state.DraggableCameraState;
import com.brainless.alchemist.model.tempImport.RendererPlatform;
import com.brainless.alchemist.view.common.SceneInputListener;
import com.brainless.alchemist.view.tab.scene.customControl.JmeImageView;
import com.jme3.app.SimpleApplication;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import util.geometry.geom2d.Point2D;

public class BaseCamera implements SceneInputListener{
	Point2D last = null;
	
	@Override
	public void onMousePressed(MouseEvent e){
		last = new Point2D(e.getX(), e.getY());
	}
	
	@Override
	public void onMouseDragged(MouseEvent e){
		Point2D vec = new Point2D(e.getX(), e.getY()).getSubtraction(last);
		if(e.isSecondaryButtonDown())
			RendererPlatform.enqueue((app) -> rotateCam(app, vec));
		else if(e.isMiddleButtonDown())
			RendererPlatform.enqueue((app) -> moveCam(app, vec));
		last = new Point2D(e.getX(), e.getY());
	}
	
	@Override
	public void onMouseReleased(MouseEvent e){
	}
	
	@Override
	public void onMouseScroll(ScrollEvent e){
		RendererPlatform.enqueue((app) -> zoomCam(app, e.getDeltaY()/e.getMultiplierY()));
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
	}
	
	@Override
	public void onKeyPressed(KeyEvent e) {
	}

	@Override
	public void onKeyReleased(KeyEvent e) {
	}
	
	static private boolean rotateCam(SimpleApplication app, Point2D vec){
		DraggableCameraState cam = app.getStateManager().getState(DraggableCameraState.class);
		cam.rotateCamera((float)vec.x, app.getCamera().getUp());
		cam.rotateCamera((float)vec.y, app.getCamera().getLeft());
		return true;
	}
	
	static private boolean zoomCam(SimpleApplication app, double amount){
		DraggableCameraState cam = app.getStateManager().getState(DraggableCameraState.class);
		cam.moveCamera((float)amount, false);
		return true;
	}
	
	static private boolean moveCam(SimpleApplication app, Point2D vec){
		DraggableCameraState cam = app.getStateManager().getState(DraggableCameraState.class);
		cam.strafeCamera((float)vec.x, true);
		cam.strafeCamera((float)vec.y, false);
		return true;
	}
	
	static private boolean setCamVelocity(SimpleApplication app, Point2D vec){
		DraggableCameraState cam = app.getStateManager().getState(DraggableCameraState.class);
		cam.setVelocity(vec);
		return true;
	}
}
