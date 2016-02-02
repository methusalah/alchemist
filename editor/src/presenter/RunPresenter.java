package presenter;

import java.util.ArrayList;
import java.util.List;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;

import controller.ECS.EntitySystem;
import controller.ECS.LogicLoop;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import presenter.common.GameInputListener;
import presenter.common.RunState;
import presenter.common.SceneInputListener;
import util.LogUtil;
import presenter.common.RunState.State;

public class RunPresenter {
	private final GameInputListener game;
	private final List<SceneInputListener> savedListeners = new ArrayList<>();
	private final IntegerProperty millisPerTickProperty = new SimpleIntegerProperty();
	
	public RunPresenter() {
		game = new GameInputListener(EditorPlatform.getScene());
		millisPerTickProperty.setValue(LogicLoop.getMillisPerTick());
		millisPerTickProperty.addListener((observable, oldValue, newValue) -> LogicLoop.setMillisPerTick(newValue.intValue()));
	}
	
	public void run(){
		savedListeners.addAll(EditorPlatform.getSceneInputManager().getListeners());
		EditorPlatform.getSceneInputManager().getListeners().clear();
		EditorPlatform.getSceneInputManager().addListener(game);

		EditorPlatform.getRunStateProperty().set(new RunState(State.Run));
		EditorPlatform.getScene().enqueue(app -> runGame(app));
	}

	public void stop(){
		EditorPlatform.getSceneInputManager().getListeners().clear();
		EditorPlatform.getSceneInputManager().getListeners().addAll(savedListeners);
		savedListeners.clear();
		
		EditorPlatform.getRunStateProperty().set(new RunState(State.Stop));
		EditorPlatform.getScene().enqueue(app -> stopGame(app));
	}

	static public boolean runGame(SimpleApplication app){
		AppStateManager stateManager = app.getStateManager();
		EntitySystem es = stateManager.getState(EntitySystem.class);
		
		es.initVisuals(true);
		es.initAudio(true);
		es.initCommand(true);
		es.initLogic(true);
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

	public IntegerProperty getMillisPerTickProperty() {
		return millisPerTickProperty;
	}
}