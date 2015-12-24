package application;
	
import presenter.WorldEditorPresenter;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3x.jfx.injfx.JmeForImageView;
import com.simsilica.es.EntityData;

import controller.ECS.EntitySystem;
import controller.ECS.SceneSelectorState;
import app.AppFacade;
import application.topDownScene.TopDownSceneController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Command;
import model.Model;
import model.ECS.EntityDataObserver;
import model.ECS.PostingEntityData;
import model.state.DraggableCameraState;
import model.state.WorldLocaliserState;
import model.state.WorldToolState;
import model.world.HeightMapTool;
import model.world.WorldData;
import util.LogUtil;
import view.Overview;


public class MainEditor extends Application {
	Controller controller;
	TopDownSceneController topDownScenecontroller;
	Overview view;
	private JmeForImageView jme;

	@Override
	public void start(Stage primaryStage) {
		LogUtil.init();
		EntityData ed = new PostingEntityData();
		EditorPlatform.setEntityData(ed, new EntityDataObserver(ed));
		EditorPlatform.setWorldData(new WorldData(EditorPlatform.getEntityData()));
		EditorPlatform.setCommand(new Command());
		
		view = new Overview(primaryStage);

		topDownScenecontroller = new TopDownSceneController(jme, view);
		controller = new Controller(model, view);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
