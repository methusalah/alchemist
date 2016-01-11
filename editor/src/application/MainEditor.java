package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import util.LogUtil;
import view.Overview;


public class MainEditor extends Application {
	Overview view;

	@Override
	public void start(Stage primaryStage) {
		LogUtil.init();
		
		view = new Overview(primaryStage);
		view.setComponentList(new MyUserComponentList());
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
