package view;

import presenter.WorldEditorPresenter;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Model;
import util.event.EventManager;
import util.event.scene.AppClosedEvent;

public class Overview {
	public final InspectorTab inspectorTab;
	public final WorldEditorTab worldEditorTab;
	public final HierarchyTab hierarchyTab;
	public final ResourceTab resourceTab;
	public final RunPanel runPanel;
	public final SceneViewer sceneViewer;
	
	public Overview(Stage stage, Model model, WorldEditorPresenter worldEditorPresenter) {
		inspectorTab = new InspectorTab(model.hierarchy.selectionProperty);
		worldEditorTab = new WorldEditorTab(worldEditorPresenter);
		hierarchyTab = new HierarchyTab();
		resourceTab = new ResourceTab();
		runPanel = new RunPanel();
		sceneViewer = new SceneViewer();
		BorderPane scenePane = new BorderPane(sceneViewer, runPanel, null, null, null);
		
		SplitPane leftRegion = new SplitPane(new TabPane(hierarchyTab), new TabPane(resourceTab));
		leftRegion.setOrientation(Orientation.VERTICAL);
		
		TabPane editors = new TabPane(inspectorTab, worldEditorTab);
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
		stage.setOnCloseRequest(e -> EventManager.post(new AppClosedEvent()));
	}
}
