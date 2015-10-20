package view;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Model;

import com.jme3x.jfx.injfx.JmeForImageView;

public class Overview {
	public final InspectorView inspectorView;
	public final HierarchyView hierarchyView;
	public final ResourceView resourceView;
	public final SceneView sceneView;
	
	public Overview(Stage stage, JmeForImageView jme, Model model) {
		inspectorView = new InspectorView(model.selectionProperty);
		hierarchyView = new HierarchyView();
		resourceView = new ResourceView();
		sceneView = new SceneView(jme);
		stage.setTitle("Entity Editor");
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		      public void handle(WindowEvent e){
				jme.stop(true);
		      }
		});
		
		SplitPane leftRegion = new SplitPane(hierarchyView, resourceView);
		leftRegion.setOrientation(Orientation.VERTICAL);
		SplitPane root = new SplitPane();
		root.setOrientation(Orientation.HORIZONTAL);
		root.setPrefSize(1600, 960);
		root.setDividerPositions(0.2, 0.8);
		root.getItems().addAll(leftRegion, sceneView, inspectorView);

		stage.setScene(new Scene(root));
		stage.show();
	}
}
