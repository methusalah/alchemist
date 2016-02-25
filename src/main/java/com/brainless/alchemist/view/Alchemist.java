package com.brainless.alchemist.view;

	
import java.io.IOException;

import com.brainless.alchemist.model.EditorPlatform;
import com.brainless.alchemist.model.ECS.data.TraversableEntityData;
import com.brainless.alchemist.model.ECS.pipeline.PipelineManager;
import com.brainless.alchemist.view.overview.Overview;
import com.brainless.alchemist.view.tab.scene.customControl.JmeImageView;
import com.brainless.alchemist.view.util.UserComponentList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.LogUtil;

public class Alchemist extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		LogUtil.init();
		// Model instanciation
		EditorPlatform.setEntityData(new TraversableEntityData());
		EditorPlatform.getUserComponentList().setValue(new UserComponentList());
		EditorPlatform.setPipelineManager(new PipelineManager());
		
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
		
		onIntialize();
		EditorPlatform.getPipelineManager().runEditionPiplines();
	}
	
	protected void onIntialize(){
		
	}
}
