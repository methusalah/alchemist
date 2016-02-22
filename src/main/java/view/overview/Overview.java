package view.overview;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import util.LogUtil;
import view.ViewPlatform;
import view.actionBar.ActionBar;
import view.tab.hierarchy.HierarchyTab;
import view.tab.inspector.InspectorTab;
import view.tab.report.ReportTab;
import view.tab.resources.ResourcesTab;
import view.tab.scene.SceneViewTab;
import view.util.ViewLoader;

public class Overview extends BorderPane {
	
	@FXML
	private TabPane hierarchyTabPane, inspectorTabPane, resourcesTabPane, sceneViewTabPane;
	
	public Overview() {
		ViewLoader.loadFXMLForControl(this);
	}
	
	@FXML
	public void initialize() {
		setTop(new ActionBar());
		ViewPlatform.hierarchyTabPane = hierarchyTabPane;
		ViewPlatform.inspectorTabPane = inspectorTabPane;
		ViewPlatform.resourcesTabPane = resourcesTabPane;
		ViewPlatform.sceneViewTabPane = sceneViewTabPane;
		
		hierarchyTabPane.getTabs().add(new HierarchyTab());
		inspectorTabPane.getTabs().addAll(new InspectorTab(), new ReportTab());
		resourcesTabPane.getTabs().add(new ResourcesTab());
		sceneViewTabPane.getTabs().add(new SceneViewTab());
	}
	
}
