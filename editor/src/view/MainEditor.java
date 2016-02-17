package view;

	
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Command;
import model.EditorPlatform;
import model.ECS.TraversableEntityData;
import util.LogUtil;
import view.overview.Overview;
import view.tab.scene.customControl.JmeImageView;
import view.util.UserComponentList;


public class MainEditor extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		LogUtil.init();

		// Model instanciation
		EditorPlatform.setEntityData(new TraversableEntityData());
		EditorPlatform.setCommand(new Command());
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
