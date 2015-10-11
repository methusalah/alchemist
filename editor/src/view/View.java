package view;

import java.io.IOException;

import application.MainEditor;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class View {
	public final InspectorView inspectorView;
	public final HierarchyView hierarchyView;
	
	public View(Stage stage) {
		inspectorView = new InspectorView();
		hierarchyView = new HierarchyView();
		stage.setTitle("Entity Editor");
		
		BorderPane root;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainEditor.class.getResource("/view/Overview.fxml")); 
		try {
			root = (BorderPane) loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			root.setRight(inspectorView);
			root.setLeft(hierarchyView);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
