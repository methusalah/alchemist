package view;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3x.jfx.injfx.JmeForImageView;
import com.sun.corba.se.impl.oa.poa.AOMEntry;

import controller.DraggableCamera;
import controller.ECS.EntitySystem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import util.LogUtil;
import util.geometry.geom2d.Point2D;

public class SceneView extends VBox {
	
	Point2D oldCoord = null;

	public SceneView(JmeForImageView jme) {
		setStyle("-fx-background-color: darkgrey");
		HBox runPane = new HBox();
		
		Button runButton = new Button("Run");
		runButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				jme.enqueue((app) -> startGame(app));
			}
		});
		runPane.getChildren().add(runButton);

		Button stopButton = new Button("Stop");
		stopButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				jme.enqueue((app) -> stopGame(app));
			}
		});
		runPane.getChildren().add(stopButton);
		
		getChildren().add(runPane);
		
		Pane scenePane = new Pane();
		ImageView image = new ImageView();
		
		
		// Camera rotation
		image.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton() == MouseButton.SECONDARY)
					oldCoord = new Point2D(event.getX(), event.getY());
			}
		});
		image.setOnMouseDragged(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton() == MouseButton.SECONDARY){
					Point2D newCoord = new Point2D(event.getX(), event.getY());
					Point2D vec = newCoord.getSubtraction(oldCoord);
					jme.enqueue((app) -> rotateCam(app, vec));
					oldCoord = newCoord;
				}
			}
		});
		
		// camera zoom
		image.setOnScroll(new EventHandler<ScrollEvent>() {

			@Override
			public void handle(ScrollEvent event) {
				jme.enqueue((app) -> moveCam(app, event.getDeltaY()/event.getMultiplierY()));
			}
		});
		
		// camera motion
		
		
//		image.setOnMouseReleased(new EventHandler<MouseEvent>() {
//
//			@Override
//			public void handle(MouseEvent event) {
//				oldCoord = null;
//				
//			}
//		});
		
//		image.setOnMouseMoved(new EventHandler<MouseEvent>() {
//
//			@Override
//			public void handle(MouseEvent event) {
//				Point2D coord = new Point2D(event.getX(), event.getY());
//				jme.enqueue((app) -> setMousePosition(app, coord));
//			}
//		});
//		image.setOnMousePressed(new EventHandler<MouseEvent>() {
//
//			@Override
//			public void handle(MouseEvent event) {
//				if(event.getButton() == MouseButton.PRIMARY)
//					jme.enqueue((app) -> setMouseAction(app, true, 0));
//				else if(event.getButton() == MouseButton.SECONDARY)
//					jme.enqueue((app) -> setMouseAction(app, true, 1));
//				else if(event.getButton() == MouseButton.MIDDLE)
//					jme.enqueue((app) -> setMouseAction(app, true, 2));
//			}
//		});
//		image.setOnMouseReleased(new EventHandler<MouseEvent>() {
//
//			@Override
//			public void handle(MouseEvent event) {
//				if(event.getButton() == MouseButton.PRIMARY)
//					jme.enqueue((app) -> setMouseAction(app, false, 0));
//				else if(event.getButton() == MouseButton.SECONDARY)
//					jme.enqueue((app) -> setMouseAction(app, false, 1));
//				else if(event.getButton() == MouseButton.MIDDLE)
//					jme.enqueue((app) -> setMouseAction(app, false, 2));
//			}
//		});
//		image.setOnKeyPressed(new EventHandler<KeyEvent>() {
//
//			@Override
//			public void handle(KeyEvent event) {
//				jme.enqueue((app) -> setKeyAction(app, true, event.getCharacter()));
//			}
//		});
//		image.setOnKeyReleased(new EventHandler<KeyEvent>() {
//
//			@Override
//			public void handle(KeyEvent event) {
//				jme.enqueue((app) -> setKeyAction(app, false, event.getCharacter()));
//			}
//		});
		

		
		image.fitHeightProperty().bind(heightProperty());
		image.fitWidthProperty().bind(widthProperty());
		image.setStyle("-fx-background-color: blue");
		scenePane.getChildren().add(image);

		getChildren().add(scenePane);
		jme.bind(image);
	}
	
	static public boolean startGame(SimpleApplication app){
		AppStateManager stateManager = app.getStateManager();
		EntitySystem es = stateManager.getState(EntitySystem.class);
		
		es.initVisuals(true);
		es.initAudio(true);
		es.initCommand(true);
		es.initLogic(true);
		return true;
	}
	
	static public boolean rotateCam(SimpleApplication app, Point2D vec){
		DraggableCamera cam = app.getStateManager().getState(DraggableCamera.class);
		cam.rotateCamera((float)vec.x, app.getCamera().getUp());
		cam.rotateCamera((float)vec.y, app.getCamera().getLeft());
		return true;
	}

	static public boolean moveCam(SimpleApplication app, double amount){
		DraggableCamera cam = app.getStateManager().getState(DraggableCamera.class);
		cam.moveCamera((float)amount, false);
		return true;
	}
	
	static public boolean setMousePosition(SimpleApplication app, Point2D coordInSceneScreen){
		return true;
	}
	static public boolean setMouseAction(SimpleApplication app, boolean pressed, Integer mouseButton){
		return true;
	}
	static public boolean setKeyAction(SimpleApplication app, boolean pressed, String character){
		return true;
	}
	
	static public boolean stopGame(SimpleApplication app){
		AppStateManager stateManager = app.getStateManager();
		EntitySystem es = stateManager.getState(EntitySystem.class);
		
		es.initVisuals(true);
		es.initAudio(false);
		es.initCommand(false);
		es.initLogic(false);
		return true;
	}
}
