package com.brainless.alchemist.view.instrument;

import com.brainless.alchemist.model.state.SceneSelectorState;
import com.brainless.alchemist.model.tempImport.RendererPlatform;
import com.brainless.alchemist.view.common.SceneInputListener;
import com.brainless.alchemist.view.tab.scene.customControl.JmeImageView;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import util.geometry.geom2d.Point2D;

public class InstrumentInputListener implements SceneInputListener {
	private final Class<? extends AbstractInstrumentState> stateClass;
	
	public InstrumentInputListener(Class<? extends AbstractInstrumentState> stateClass) {
		this.stateClass = stateClass;
	}

	@Override
	public void onMousePressed(MouseEvent e){
		if(e.getButton() == MouseButton.PRIMARY)
			RendererPlatform.enqueue(app -> app.getStateManager().getState(stateClass).startDrag());
	}

	@Override
	public void onMouseMoved(MouseEvent e){
		RendererPlatform.enqueue(app -> setSceneMouseCoord(app, new Point2D(e.getX(), e.getY())));
	}

	@Override
	public void onMouseReleased(MouseEvent e){
		if(e.getButton() == MouseButton.PRIMARY)
			RendererPlatform.enqueue(app -> app.getStateManager().getState(stateClass).stopDrag());
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
		RendererPlatform.enqueue(app -> setSceneMouseCoord(app, new Point2D(e.getX(), e.getY())));
		if(e.getButton() == MouseButton.PRIMARY)
			RendererPlatform.enqueue(app -> app.getStateManager().getState(stateClass).drag());
	}

	static private boolean setSceneMouseCoord(SimpleApplication app, Point2D coord) {
		AppStateManager stateManager = app.getStateManager();
		stateManager.getState(SceneSelectorState.class).setCoordInScreenSpace(coord);
		return true;
	}
}
