package view;

import java.util.HashMap;
import java.util.Map;

import util.LogUtil;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeDesktopSystem;
import com.jme3x.jfx.injfx.JmeForImageView;

import controller.ECS.EntitySystem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SceneView extends VBox {

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
		
		AppSettings settings = new AppSettings(true);
		// important to use those settings
		settings.setFullscreen(false);
		settings.setUseInput(true);
		settings.setFrameRate(30);
		settings.setCustomRenderer(com.jme3x.jfx.injfx.JmeContextOffscreenSurface.class);
		LogUtil.info("mouse : "+app.getContext().getMouseInput());
		LogUtil.info("keybo : "+app.getContext().getKeyInput());
		app.setSettings(settings);
		
//		JmeDesktopSystem sys = new JmeDesktopSystem()
		app.restart();
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
	
	
	static class KeyActions {
		public Runnable onPressed;
		public Runnable onReleased;

		public KeyActions(Runnable onPressed, Runnable onReleased) {
			super();
			this.onPressed = onPressed;
			this.onReleased = onReleased;
		}
	}
//	public static void bindDefaults(ImageView c, CameraDriverInput driver) {
//		c.setFocusTraversable(true);
//		c.hoverProperty().addListener((ob, o, n)->{
//			if(n) c.requestFocus();
//		});
//		c.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{c.requestFocus();});
//		
//		Map<KeyCode, KeyActions> inputMap = new HashMap<>();
//		inputMap.put(KeyCode.PAGE_UP, new KeyActions(driver::upPressed, driver::upReleased));
//		inputMap.put(KeyCode.PAGE_DOWN, new KeyActions(driver::downPressed, driver::downReleased));
//
//		// arrow
//		inputMap.put(KeyCode.UP, new KeyActions(driver::forwardPressed, driver::forwardReleased));
//		inputMap.put(KeyCode.LEFT, new KeyActions(driver::leftPressed, driver::leftReleased));
//		inputMap.put(KeyCode.DOWN, new KeyActions(driver::backwardPressed, driver::backwardReleased));
//		inputMap.put(KeyCode.RIGHT, new KeyActions(driver::rightPressed, driver::rightReleased));
//
//		//WASD
//		inputMap.put(KeyCode.W, new KeyActions(driver::forwardPressed, driver::forwardReleased));
//		inputMap.put(KeyCode.A, new KeyActions(driver::leftPressed, driver::leftReleased));
//		inputMap.put(KeyCode.S, new KeyActions(driver::backwardPressed, driver::backwardReleased));
//		inputMap.put(KeyCode.D, new KeyActions(driver::rightPressed, driver::rightReleased));
//
//		// ZQSD
//		inputMap.put(KeyCode.Z, new KeyActions(driver::forwardPressed, driver::forwardReleased));
//		inputMap.put(KeyCode.Q, new KeyActions(driver::leftPressed, driver::leftReleased));
//		c.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
//			KeyActions ka = inputMap.get(event.getCode());
//			if (ka != null) ka.onPressed.run();
//	        event.consume();
//	    });
//		c.addEventHandler(KeyEvent.KEY_RELEASED, (event) -> {
//			KeyActions ka = inputMap.get(event.getCode());
//			if (ka != null) ka.onReleased.run();
//	        event.consume();
//	    });
//	}
}
