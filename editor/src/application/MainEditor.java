package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import util.LogUtil;
import view.Overview;


public class MainEditor extends Application {
	Model model;
	Controller controller;
	Overview view;
	
	@Override
	public void start(Stage primaryStage) {
		LogUtil.init();
		model = new Model();
		view = new Overview(primaryStage, model.jme);
		controller = new Controller(model, view);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
