package view;

import com.jme3.app.SimpleApplication;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import model.state.DraggableCameraState;
import util.geometry.geom2d.Point2D;
import view.common.SceneInputListener;
import view.tab.scene.customControl.JmeImageView;

public class BaseCamera implements SceneInputListener{
	private final JmeImageView jme;
	
	Point2D last = null;
	
	public BaseCamera(JmeImageView jme) {
		this.jme = jme;
	}
	
	@Override
	public void onMousePressed(MouseEvent e){
		last = new Point2D(e.getX(), e.getY());
	}
	
	@Override
	public void onMouseDragged(MouseEvent e){
		Point2D vec = new Point2D(e.getX(), e.getY()).getSubtraction(last);
		if(e.isSecondaryButtonDown())
			jme.enqueue((app) -> rotateCam(app, vec));
		else if(e.isMiddleButtonDown())
			jme.enqueue((app) -> moveCam(app, vec));
		last = new Point2D(e.getX(), e.getY());
	}
	
	@Override
	public void onMouseReleased(MouseEvent e){
	}
	
	@Override
	public void onMouseScroll(ScrollEvent e){
		jme.enqueue((app) -> zoomCam(app, e.getDeltaY()/e.getMultiplierY()));
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
