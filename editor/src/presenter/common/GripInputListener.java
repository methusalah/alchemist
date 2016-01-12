package presenter.common;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3x.jfx.injfx.JmeForImageView;
import com.simsilica.es.EntityId;

import controller.ECS.SceneSelectorState;
import javafx.application.Platform;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import jdk.internal.org.objectweb.asm.Handle;
import model.state.GripState;
import model.state.WorldToolState;
import model.world.Tool;
import presenter.EditorPlatform;
import util.geometry.geom2d.Point2D;
import view.controls.JmeImageView;

public class GripInputListener implements SceneInputListener {
	private final JmeImageView jme;
	
	public GripInputListener(JmeImageView jme) {
		this.jme = jme;
	}

	@Override
	public void onMousePressed(MouseEvent e){
		if(e.getButton() == MouseButton.PRIMARY)
			jme.enqueue(app -> {
				app.getStateManager().getState(GripState.class).grab(new Point2D(e.getX(), e.getY()));
				return true;
			});
	}

	@Override
	public void onMouseMoved(MouseEvent e){
		jme.enqueue(app -> setSceneMouseCoord(app, new Point2D(e.getX(), e.getY())));
	}

	@Override
	public void onMouseReleased(MouseEvent e){
		if(e.getButton() == MouseButton.PRIMARY)
			jme.enqueue(app -> {
				app.getStateManager().getState(GripState.class).release();
				return true;
			});
	}
	
	@Override
	public void onMouseScroll(ScrollEvent e){
	}

	@Override
	public void onKeyPressed(KeyEvent e){
	}
	
	@Override
	public void onKeyReleased(KeyEvent e){
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
		if(e.getButton() == MouseButton.PRIMARY)
			jme.enqueue(app -> {
				app.getStateManager().getState(GripState.class).drag(new Point2D(e.getX(), e.getY()));
				return true;
			});
	}

	static private boolean setSceneMouseCoord(SimpleApplication app, Point2D coord) {
		AppStateManager stateManager = app.getStateManager();
		stateManager.getState(SceneSelectorState.class).setCoordInScreenSpace(coord);
		return true;
	}
}
