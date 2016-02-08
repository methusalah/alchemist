package presenter;

import java.util.ArrayList;
import java.util.List;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;

import controller.ECS.EntitySystem;
import controller.ECS.LogicLoop;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.ECS.EntityDataMemento;
import presenter.common.GameInputListener;
import presenter.common.RunState;
import presenter.common.RunState.State;
import presenter.common.SceneInputListener;

public class RunPresenter {
	private final GameInputListener game;
	private final List<SceneInputListener> savedListeners = new ArrayList<>();
	private final IntegerProperty millisPerTickProperty = new SimpleIntegerProperty();
	private EntityDataMemento memento; 
	
	public RunPresenter() {
		game = new GameInputListener(EditorPlatform.getScene());
		millisPerTickProperty.setValue(LogicLoop.getMillisPerTick());
		millisPerTickProperty.addListener((observable, oldValue, newValue) -> LogicLoop.setMillisPerTick(newValue.intValue()));
	}
	
	public void run(){
		memento = EditorPlatform.getEntityData().createMemento();
		
		savedListeners.addAll(EditorPlatform.getSceneInputManager().getListeners());
		EditorPlatform.getSceneInputManager().getListeners().clear();
		EditorPlatform.getSceneInputManager().addListener(game);

		EditorPlatform.getRunStateProperty().set(new RunState(State.Run));
		EditorPlatform.getScene().enqueue(app -> runGame(app));
	}

	public void stop(){
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