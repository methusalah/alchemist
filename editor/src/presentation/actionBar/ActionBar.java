package presentation.actionBar;

import java.util.ArrayList;
import java.util.List;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;

import controller.ECS.EntitySystem;
import controller.ECS.LogicLoop;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.NumberStringConverter;
import model.EditorPlatform;
import model.ECS.EntityDataMemento;
import presentation.common.GameInputListener;
import presentation.common.RunState;
import presentation.common.SceneInputListener;
import presentation.common.RunState.State;
import presentation.util.ViewLoader;
import util.LogUtil;

public class ActionBar extends BorderPane {
	
	@FXML
	private Slider tickDurationSlider;
	
	@FXML
	private TextField tickDurationField;
	
	@FXML
	private ToggleButton playButton;
	
	public ActionBar() {
		ViewLoader.loadFXMLForControl(this);
		
		// presentation init
		game = new GameInputListener(EditorPlatform.getScene());
		millisPerTickProperty.setValue(LogicLoop.getMillisPerTick());
		millisPerTickProperty.addListener((observable, oldValue, newValue) -> LogicLoop.setMillisPerTick(newValue.intValue()));
	}

	@FXML
	private void initialize(){
		// view init
		tickDurationSlider.valueProperty().bindBidirectional(millisPerTickProperty);
		tickDurationField.textProperty().bindBidirectional(millisPerTickProperty, new NumberStringConverter());
		playButton.selectedProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue)
				run();
			else
				stop();
		});
	}
	
	// PRESENTATION
	
	private final GameInputListener game;
	private final List<SceneInputListener> savedListeners = new ArrayList<>();
	private final IntegerProperty millisPerTickProperty = new SimpleIntegerProperty();
	private EntityDataMemento memento; 

	public void run(){
		LogUtil.info("start !");
		memento = EditorPlatform.getEntityData().createMemento();
		
		savedListeners.addAll(EditorPlatform.getSceneInputManager().getListeners());
		EditorPlatform.getSceneInputManager().getListeners().clear();
		EditorPlatform.getSceneInputManager().addListener(game);

		EditorPlatform.getRunStateProperty().set(new RunState(State.Run));
		EditorPlatform.getScene().enqueue(app -> runGame(app));
	}

	public void stop(){
		LogUtil.info("stop !");
		// entity data restoring
		EditorPlatform.getSceneInputManager().getListeners().clear();
		EditorPlatform.getSceneInputManager().getListeners().addAll(savedListeners);
		savedListeners.clear();
		
		EditorPlatform.getRunStateProperty().set(new RunState(State.Stop));
		EditorPlatform.getScene().enqueue(app -> stopGame(app));
	}

	public boolean runGame(SimpleApplication app){
		AppStateManager stateManager = app.getStateManager();
		EntitySystem es = stateManager.getState(EntitySystem.class);
		
		es.initVisuals(true);
		es.initAudio(true);
		es.initCommand(true);
		es.initLogic(true);
		return true;
	}

	public boolean stopGame(SimpleApplication app){
		AppStateManager stateManager = app.getStateManager();
		EntitySystem es = stateManager.getState(EntitySystem.class);
		
		es.initVisuals(true);
		es.initAudio(false);
		es.initCommand(false);
		es.initLogic(false);
		
		// state has to be set in the jme thread, to ensure that no entity is added after cleanup
		EditorPlatform.getEntityData().setMemento(memento);
		return true;
	}

	public IntegerProperty getMillisPerTickProperty() {
		return millisPerTickProperty;
	}
}
