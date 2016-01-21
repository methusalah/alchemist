package view.instrument;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;

import controller.ECS.SceneSelectorState;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import presenter.common.SceneInputListener;
import util.geometry.geom2d.Point2D;
import view.controls.jmeScene.JmeImageView;

public class InstrumentInputListener implements SceneInputListener {
	private final JmeImageView jme;
	private final Class<? extends AbstractInstrumentState> stateClass;
	
	public InstrumentInputListener(JmeImageView jme, Class<? extends AbstractInstrumentState> stateClass) {
		this.jme = jme;
		this.stateClass = stateClass;
	}

	@Override
	public void onMousePressed(MouseEvent e){
		if(e.getButton() == MouseButton.PRIMARY)
			jme.enqueue(app -> {app.getStateManager().getState(stateClass).startDrag(); return true;});
	}

	@Override
	public void onMouseMoved(MouseEvent e){
		jme.enqueue(app -> setSceneMouseCoord(app, new Point2D(e.getX(), e.getY())));
	}

	@Override
	public void onMouseReleased(MouseEvent e){
		if(e.getButton() == MouseButton.PRIMARY)
			jme.enqueue(app -> {app.getStateManager().getState(stateClass).stopDrag(); return true;});
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
		jme.enqueue(app -> setSceneMouseCoord(app, new Point2D(e.getX(), e.getY())));
		if(e.getButton() == MouseButton.PRIMARY)
			jme.enqueue(app -> {app.getStateManager().getState(stateClass).drag(); return true;});
	}

	static private boolean setSceneMouseCoord(SimpleApplication app, Point2D coord) {
		AppStateManager stateManager = app.getStateManager();
		stateManager.getState(SceneSelectorState.class).setCoordInScreenSpace(coord);
		return true;
	}
}
