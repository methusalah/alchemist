package view;

	
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.EditorPlatform;
import model.ECS.data.TraversableEntityData;
import model.ECS.pipeline.PipelineProvider;
import view.overview.Overview;
import view.tab.scene.customControl.JmeImageView;
import view.util.UserComponentList;

public class Alchemist extends Application {

	protected PipelineProvider pipelineProvider = new PipelineProvider();
	
	@Override
	public void start(Stage primaryStage) throws IOException {
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
		
		s.getStylesheets().add("resources/darktheme.css");
		
		primaryStage.setScene(s);
		primaryStage.show();
		primaryStage.setTitle("Alchimist, Zay's Entity Editor");
		primaryStage.setOnCloseRequest(e -> EditorPlatform.getScene().stop(false));
		
		
	}
	
	protected void onIntialize(){
		
	}
}