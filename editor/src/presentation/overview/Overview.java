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
import presentation.report.ReportTab;
import presentation.resources.ResourcesTab;
import presentation.scene.SceneTab;
import presentation.util.ViewLoader;
import presentation.worldEditor.WorldEditorTab;

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
		inspectorTabPane.getTabs().addAll(new InspectorTab(), new ReportTab(), new WorldEditorTab());
		resourcesTabPane.getTabs().add(new ResourcesTab());
		sceneViewTabPane.getTabs().add(new SceneTab());
		
	}
	
}
