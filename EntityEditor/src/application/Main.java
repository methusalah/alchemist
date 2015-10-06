package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.ES.serial.BlueprintLibrary;
import util.LogUtil;
import view.InspectorController;


public class Main extends Application {
	private Stage primaryStage;
	BorderPane root;
	
	@Override
	public void start(Stage primaryStage) {
		LogUtil.init();
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Entity Blueprint Editor");
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/view/Overview.fxml")); 
		try {
			root = (BorderPane) loader.load();
			Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/view/Inspector.fxml"));
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
