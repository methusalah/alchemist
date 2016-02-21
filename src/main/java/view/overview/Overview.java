package view.overview;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import model.EditorPlatform;
import plugin.infiniteWorld.editor.view.WorldEditorTab;
import view.actionBar.ActionBar;
import view.instrument.circleCollisionShape.CircleCollisionShapeInstrument;
import view.instrument.planarStance.PlanarStanceInstrument;
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
		hierarchyTabPane.getTabs().add(new HierarchyTab());
		inspectorTabPane.getTabs().addAll(new InspectorTab(), new ReportTab(), new WorldEditorTab());
		resourcesTabPane.getTabs().add(new ResourcesTab());
		sceneViewTabPane.getTabs().add(new SceneViewTab());
		
		new PlanarStanceInstrument(EditorPlatform.getScene());
		new CircleCollisionShapeInstrument(EditorPlatform.getScene());
		
	}
	
}
