package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import util.LogUtil;
import view.View;


public class MainEditor extends Application {
	Model model;
	Controller controller;
	View view;
	
	@Override
	public void start(Stage primaryStage) {
		LogUtil.init();
		model = new Model();
		view = new View(primaryStage, model.jme);
		controller = new Controller(model, view);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
