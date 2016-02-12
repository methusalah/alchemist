package presentation;

	
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Command;
import model.EditorPlatform;
import model.ECS.TraversableEntityData;
import presentation.overview.Overview;
import presentation.util.UserComponentList;
import util.LogUtil;


public class MainEditor extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		LogUtil.init();

		// Model instanciation
		EditorPlatform.setEntityData(new TraversableEntityData());
		EditorPlatform.setCommand(new Command());
		EditorPlatform.getUserComponentList().setValue(new UserComponentList());

		// View instanciation
		Overview overview = new Overview();
		
		Scene s = new Scene(overview);
		try {
			s.getStylesheets().add(new File("assets/interface/darktheme.css").toURI().toURL().toString());
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		//((presentation.scene.SceneView)sceneViewer.getContent()).registerKeyInputs(s);
		primaryStage.setScene(s);
		primaryStage.show();
		primaryStage.setTitle("Alchimist, Zay's Entity Editor");
		primaryStage.setOnCloseRequest(e -> EditorPlatform.getScene().stop(false));
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
