package view;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.system.AppSettings;
import com.jme3x.jfx.injfx.JmeForImageView;

import controller.ECS.EntitySystem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
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
		app.setSettings(settings);
		
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
}
