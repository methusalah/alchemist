package plugin;

import com.brainless.alchemist.model.state.DraggableCameraState;
import com.brainless.alchemist.model.tempImport.RendererPlatform;
import com.brainless.alchemist.view.common.SceneInputListener;
import com.brainless.alchemist.view.tab.scene.customControl.JmeImageView;
import com.jme3.app.SimpleApplication;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import util.LogUtil;
import util.geometry.geom2d.Point2D;

public class TopDownCamInputListener implements SceneInputListener{
	private boolean zPressed = false, sPressed = false, qPressed = false, dPressed = false;
	private Point2D camVelocity = Point2D.ORIGIN;

	@Override
	public void onMousePressed(MouseEvent e){
	}
	
	@Override
	public void onMouseDragged(MouseEvent e){
	}
	
	@Override
	public void onMouseReleased(MouseEvent e){
	}
	
	@Override
	public void onMouseScroll(ScrollEvent e){
		RendererPlatform.enqueue((app) -> zoomCam(app, e.getDeltaY()/e.getMultiplierY()));
	}

	@Override
	public void onKeyPressed(KeyEvent e){
		if(e.getCode() == KeyCode.UP && !zPressed){
			camVelocity = camVelocity.getAddition(0, 1);
			zPressed = true;
		} else if(e.getCode() == KeyCode.DOWN && !sPressed){
			camVelocity = camVelocity.getAddition(0, -1);
			sPressed = true;
		} else if(e.getCode() == KeyCode.LEFT && !qPressed){
			camVelocity = camVelocity.getAddition(-1, 0);
			qPressed = true;
		} else if(e.getCode() == KeyCode.RIGHT && !dPressed){
			camVelocity = camVelocity.getAddition(1, 0);
			dPressed = true;
		}
		
		RendererPlatform.enqueue((app) -> {
			setCamVelocity(app, camVelocity);
		});
	}
	
	@Override
	public void onKeyReleased(KeyEvent e){
		if(e.getCode() == KeyCode.UP){
			camVelocity = camVelocity.getAddition(0, -1);
			zPressed = false;
		} else if(e.getCode() == KeyCode.DOWN){
			camVelocity = camVelocity.getAddition(0, 1);
			sPressed = false;
		} else if(e.getCode() == KeyCode.LEFT){
			camVelocity = camVelocity.getAddition(1, 0);
			qPressed = false;
		} else if(e.getCode() == KeyCode.RIGHT){
			camVelocity = camVelocity.getAddition(-1, 0);
			dPressed = false;
		}
		RendererPlatform.enqueue((app) -> setCamVelocity(app, camVelocity));
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
	
	static private boolean setCamVelocity(SimpleApplication app, Point2D vec){
		DraggableCameraState cam = app.getStateManager().getState(DraggableCameraState.class);
		cam.setVelocity(vec);
		return true;
	}
}
