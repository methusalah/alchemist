package view;

import application.EditorPlatform;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Overview {
	public final SceneView sceneViewer;
	
	public Overview(Stage stage) {
		sceneViewer = new SceneView();
		
		BorderPane scenePane = new BorderPane(sceneViewer, new RunPanel(), null, null, null);
		
		SplitPane leftRegion = new SplitPane(new TabPane(new HierarchyTab()), new TabPane(new ResourceTab()));
		leftRegion.setOrientation(Orientation.VERTICAL);
		
		TabPane editors = new TabPane(new InspectorTab(), new WorldEditorTab(), new ReportTab());
		editors.setMinWidth(300);
		editors.setMaxWidth(500);

		
		SplitPane root = new SplitPane();
		root.setOrientation(Orientation.HORIZONTAL);
		root.setPrefSize(1600, 960);
		root.setDividerPositions(0.2, 0.8);
		root.getItems().addAll(leftRegion, scenePane, editors);
		

		Scene s = new Scene(root);
		sceneViewer.registerKeyInputs(s);
		stage.setScene(s);
		stage.show();
		stage.setTitle("Entity Editor");
		stage.setOnCloseRequest(e -> EditorPlatform.getScene().stop(false));
	}
}
