/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.editor;

import model.ModelManager;
import model.editor.ToolManager;
import util.event.BattleFieldUpdateEvent;
import util.event.EventManager;
import util.geometry.geom2d.Point2D;
import view.EditorView;
import view.View;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;

import controller.AppState;
import controller.SpatialSelector;
import controller.cameraManagement.IsometricCameraManager;

/**
 *
 * @author Benoît
 */
public class EditorState extends AppState {
	protected Point2D screenCoord;

	@Inject
	public EditorState(EditorView ev, EditorInputInterpreter ii, SpatialSelector ss, IsometricCameraManager icm) {
		super(ev, ii, ss, icm);
		ss.centered = false;
//		view = new View();
		//super();
//		inputInterpreter = new EditorInputInterpreter(this);
//		guiController = new EditorGUIController(nifty, this);
//		cameraManager = new IsometricCameraManager(cam, 10);
//
//		EventManager.register(this);
	}

	@Override
	public void update(float elapsedTime) {
//		//        screenCoord = Translator.toPoint2D(im.getCursorPosition());
//		ToolManager.setPointedSpatialLabel(spatialSelector.getSpatialLabel());
//		ToolManager.setPointedSpatialEntityId(spatialSelector.getEntityId());
//		if(view.editorRend != null){
//			Point2D coord = spatialSelector.getCoord(view.editorRend.gridNode);
//			if (coord != null &&
//					ModelManager.battlefieldReady && 
//					ModelManager.getBattlefield().getMap().isInBounds(coord)) {
//				ToolManager.updatePencilsPos(coord);
//				view.editorRend.drawPencil();
//			}
//		}
//
//		guiController.update();
	}

	@Override
	public void stateAttached(AppStateManager stateManager) {
		super.stateAttached(stateManager);
//		inputManager.setCursorVisible(true);
//		view.getRootNode().attachChild(view.editorRend.mainNode);
//		guiController.activate();
//		if (ModelManager.getBattlefield() != null) {
//			ModelManager.getBattlefield().getEngagement().reset();
//		}
	}

	@Override
	public void stateDetached(AppStateManager stateManager) {
//		ModelManager.getBattlefield().getEngagement().save();
		super.stateDetached(stateManager);
//		view.getRootNode().detachChild(view.editorRend.mainNode);
	}

	@Subscribe
	public void manageEvent(BattleFieldUpdateEvent ev) {
		((IsometricCameraManager)cameraManager).move(ModelManager.getBattlefield().getMap().xSize() / 2, ModelManager.getBattlefield().getMap().ySize() / 2);
	}

}
