package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import model.Command;
import model.ECS.EntityDataObserver;
import model.ECS.PostingEntityData;
import model.world.WorldData;
import util.LogUtil;
import view.Overview;
import application.topDownScene.TopDownSceneController;

import com.jme3x.jfx.injfx.JmeForImageView;
import com.simsilica.es.EntityData;


public class MainEditor extends Application {
	TopDownSceneController topDownScenecontroller;
	Overview view;

	@Override
	public void start(Stage primaryStage) {
		LogUtil.init();
		EntityData ed = new PostingEntityData();
		EditorPlatform.setEntityData(ed, new EntityDataObserver(ed));
		EditorPlatform.setWorldData(new WorldData(EditorPlatform.getEntityData()));
		EditorPlatform.setCommand(new Command());
		
		view = new Overview(primaryStage);

		topDownScenecontroller = new TopDownSceneController(view);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
