package application;
	
import java.io.IOException;

import com.simsilica.es.base.DefaultEntityData;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.EntityIntrospector;
import model.ES.serial.BlueprintLibrary;
import util.LogUtil;
import view.InspectorController;


public class MainEditor extends Application {
	private Stage primaryStage;
	BorderPane root;
	
	@Override
	public void start(Stage primaryStage) {
		LogUtil.init();
		DefaultEntityData ed = new DefaultEntityData();
		
		
		EntityIntrospector m = new EntityIntrospector(ed);
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Entity Editor");
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainEditor.class.getResource("/view/Overview.fxml")); 
		try {
			root = (BorderPane) loader.load();
			Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		
		loader = new FXMLLoader();
		loader.setLocation(MainEditor.class.getResource("/view/Inspector.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		root.setRight(loader.getRoot());
		InspectorController ctrl = loader.getController();
		ctrl.setBlueprint(BlueprintLibrary.get("player ship"));
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
