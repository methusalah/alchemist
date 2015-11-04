package view;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Model;
import util.event.EventManager;
import util.event.scene.AppClosedEvent;

public class Overview {
	public final InspectorView inspectorView;
	public final HierarchyView hierarchyView;
	public final ResourceView resourceView;
	public final RunPanel runPanel;
	public final SceneViewer sceneViewer;
	
	public Overview(Stage stage, Model model) {
		inspectorView = new InspectorView(model.selectionProperty);
		hierarchyView = new HierarchyView();
		resourceView = new ResourceView();
		runPanel = new RunPanel();
		sceneViewer = new SceneViewer();
		BorderPane scenePane = new BorderPane(sceneViewer, runPanel, null, null, null);
		
		SplitPane leftRegion = new SplitPane(hierarchyView, resourceView);
		leftRegion.setOrientation(Orientation.VERTICAL);
		
		SplitPane root = new SplitPane();
		root.setOrientation(Orientation.HORIZONTAL);
		root.setPrefSize(1600, 960);
		root.setDividerPositions(0.2, 0.8);
		root.getItems().addAll(leftRegion, scenePane, inspectorView);
		

		Scene s = new Scene(root);
		sceneViewer.registerKeyInputs(s);
		stage.setScene(s);
		stage.show();
		stage.setTitle("Entity Editor");
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		      public void handle(WindowEvent e){
		    	  EventManager.post(new AppClosedEvent());
		      }
		});
	}
}
