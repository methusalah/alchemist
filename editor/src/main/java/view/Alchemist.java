package main.java.view;

	
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.model.EditorPlatform;
import main.java.model.ECS.TraversableEntityData;
import main.java.view.overview.Overview;
import main.java.view.tab.scene.customControl.JmeImageView;
import main.java.view.util.UserComponentList;
import util.LogUtil;


public class Alchemist extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		LogUtil.init();

		// Model instanciation
		EditorPlatform.setEntityData(new TraversableEntityData());
		EditorPlatform.getUserComponentList().setValue(new UserComponentList());

		if(EditorPlatform.getScene() == null){
			JmeImageView jmeScene = new JmeImageView();
			EditorPlatform.setScene(jmeScene);
		}

		// View instanciation
		Overview overview = new Overview();
		
		Scene s = new Scene(overview);
		ViewPlatform.JavaFXScene.setValue(s);
		
		s.getStylesheets().add("resources/darktheme.css");//File("assets/interface/darktheme.css").toURI().toURL().toString());
		
		primaryStage.setScene(s);
		primaryStage.show();
		primaryStage.setTitle("Alchimist, Zay's Entity Editor");
		primaryStage.setOnCloseRequest(e -> EditorPlatform.getScene().stop(false));
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
