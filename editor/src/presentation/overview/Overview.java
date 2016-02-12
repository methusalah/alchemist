package presentation.overview;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import model.Command;
import model.EditorPlatform;
import model.ECS.TraversableEntityData;
import presentation.actionBar.ActionBar;
import presentation.hierarchy.Hierarchy;
import presentation.hierarchy.HierarchyTab;
import presentation.inspector.InspectorTab;
import presentation.resources.ResourcesTab;
import presentation.scene.SceneTab;
import presentation.util.ViewLoader;

public class Overview extends BorderPane {
	
	@FXML
	private TabPane hierarchyTabPane, inspectorTabPane, resourcesTabPane, sceneViewTabPane;
	
	public Overview() {
		ViewLoader.loadFXMLForControl(this);
	}
	
	@FXML
	public void initialize() {
		setTop(new ActionBar());
		hierarchyTabPane.getTabs().add(new HierarchyTab());
		inspectorTabPane.getTabs().add(new InspectorTab());
		resourcesTabPane.getTabs().add(new ResourcesTab());
		sceneViewTabPane.getTabs().add(new SceneTab());
		
	}
	
}
