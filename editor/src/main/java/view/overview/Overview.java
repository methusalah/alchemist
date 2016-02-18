package main.java.view.overview;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import main.java.model.EditorPlatform;
import main.java.view.actionBar.ActionBar;
import main.java.view.instrument.circleCollisionShape.CircleCollisionShapeInstrument;
import main.java.view.instrument.planarStance.PlanarStanceInstrument;
import main.java.view.tab.hierarchy.HierarchyTab;
import main.java.view.tab.inspector.InspectorTab;
import main.java.view.tab.report.ReportTab;
import main.java.view.tab.resources.ResourcesTab;
import main.java.view.tab.scene.SceneViewTab;
import main.java.view.tab.worldEditor.WorldEditorTab;
import main.java.view.util.ViewLoader;

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
		sceneViewTabPane.getTabs().add(new SceneViewTab());
		
		new PlanarStanceInstrument(EditorPlatform.getScene());
		new CircleCollisionShapeInstrument(EditorPlatform.getScene());
		
	}
	
}
