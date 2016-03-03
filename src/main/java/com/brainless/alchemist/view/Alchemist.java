package com.brainless.alchemist.view;

	
import java.io.IOException;
import java.util.prefs.Preferences;

import com.brainless.alchemist.model.EditorPlatform;
import com.brainless.alchemist.model.ECS.data.TraversableEntityData;
import com.brainless.alchemist.model.ECS.pipeline.PipelineManager;
import com.brainless.alchemist.model.tempImport.RendererPlatform;
import com.brainless.alchemist.view.overview.Overview;
import com.brainless.alchemist.view.tab.scene.customControl.JmeImageView;
import com.brainless.alchemist.view.util.UserComponentList;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.LogUtil;

public class Alchemist extends Application {

	private Stage primaryStage;
	double x, y, width, height;
//	private DoubleProperty x = new SimpleDoubleProperty();
//	private DoubleProperty y = new SimpleDoubleProperty();
//	private DoubleProperty width = new SimpleDoubleProperty();
//	private DoubleProperty height = new SimpleDoubleProperty();
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		LogUtil.init();
		this.primaryStage = primaryStage;
		// Model instanciation
		EditorPlatform.setEntityData(new TraversableEntityData());
		EditorPlatform.getUserComponentList().setValue(new UserComponentList());
		EditorPlatform.setPipelineManager(new PipelineManager());
		
		// creation of the jme application
		ViewPlatform.setScene(new JmeImageView());
		RendererPlatform.setApp(ViewPlatform.getScene().getApp());

		// View instanciation
		Overview overview = new Overview();
		
		Scene s = new Scene(overview);
		ViewPlatform.JavaFXScene.setValue(s);
		
		s.getStylesheets().add("resources/darktheme.css");
		
	    // get window location from user preferences: use x=100, y=100, width=400, height=400 as default
		Preferences userPrefs = Preferences.userNodeForPackage(getClass());
		primaryStage.xProperty().addListener((obs, oldVal, newVal) -> x = primaryStage.isMaximized()? x : newVal.doubleValue());
		primaryStage.yProperty().addListener((obs, oldVal, newVal) -> y = primaryStage.isMaximized()? y : newVal.doubleValue());
		primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> width = primaryStage.isMaximized()? width : newVal.doubleValue());
		primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> height = primaryStage.isMaximized()? height : newVal.doubleValue());

	    primaryStage.setX(userPrefs.getDouble("stage.x", 100));
	    primaryStage.setY(userPrefs.getDouble("stage.y", 100));
	    primaryStage.setWidth(userPrefs.getDouble("stage.width", 800));
	    primaryStage.setHeight(userPrefs.getDouble("stage.height", 600));
	    primaryStage.setMaximized(userPrefs.getBoolean("stage.maximized", false));
	    
		// finalize the scene
		primaryStage.setScene(s);
		primaryStage.show();
		primaryStage.setTitle("Alchimist, Zay's Entity Editor");
		primaryStage.setOnCloseRequest(e -> ViewPlatform.getScene().stop(false));
		
		onIntialize();
		EditorPlatform.getPipelineManager().runEditionPiplines();
	}
	
	@Override
	  public void stop() {
	    Preferences userPrefs = Preferences.userNodeForPackage(getClass());
	    userPrefs.putBoolean("stage.maximized", primaryStage.isMaximized());
	    userPrefs.putDouble("stage.x", x);
	    userPrefs.putDouble("stage.y", y);
	    userPrefs.putDouble("stage.width", width);
	    userPrefs.putDouble("stage.height", height);
	    EditorPlatform.getPipelineManager().stopPiplines();
	  }
	
	protected void onIntialize(){
		
	}
}
