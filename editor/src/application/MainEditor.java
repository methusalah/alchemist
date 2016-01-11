package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import model.Command;
import model.ECS.TraversableEntityData;
import model.world.WorldData;
import util.LogUtil;
import view.Overview;


public class MainEditor extends Application {
	Overview view;

	@Override
	public void start(Stage primaryStage) {
		LogUtil.init();
		EditorPlatform.setEntityData(new TraversableEntityData());
		EditorPlatform.setWorldData(new WorldData(EditorPlatform.getEntityData()));
		EditorPlatform.setCommand(new Command());
		
		view = new Overview(primaryStage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
