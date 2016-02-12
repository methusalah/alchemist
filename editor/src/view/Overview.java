package view;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import presentation.resources.ResourcesTab;
import presenter.EditorPlatform;
import presenter.OverviewPresenter;
import presenter.util.UserComponentList;
import view.instrument.circleCollisionShape.CircleCollisionShapeInstrument;
import view.instrument.planarStance.PlanarStanceInstrument;

public class Overview {
	private final OverviewPresenter presenter = new OverviewPresenter();
	
	public Overview(Stage stage) throws IOException {
		SceneView sceneViewer = new SceneView();
		
		BorderPane scenePane = new BorderPane(sceneViewer, new RunPanel(), null, null, null);
		SplitPane leftRegion = new SplitPane(new TabPane(new presentation.hierarchy.HierarchyTab()), new TabPane(new ResourcesTab()));
		leftRegion.setOrientation(Orientation.VERTICAL);
		
		TabPane editors = new TabPane(new presentation.inspector.InspectorTab(), new WorldEditorTab(), new ReportTab());
		editors.setMinWidth(300);
		editors.setMaxWidth(500);

		
		SplitPane root = new SplitPane();
		root.setOrientation(Orientation.HORIZONTAL);
		root.setPrefSize(1600, 960);
		root.setDividerPositions(0.2, 0.8);
		root.getItems().addAll(leftRegion, scenePane, editors);

		new PlanarStanceInstrument(EditorPlatform.getScene());
		new CircleCollisionShapeInstrument(EditorPlatform.getScene());
		
		

		//Scene s = new Scene(FXMLLoader.load(presentation.overview.Overview.class.getResource("test.fxml")));
		Scene s = new Scene(root);
		try {
			s.getStylesheets().add(new File("assets/interface/darktheme.css").toURI().toURL().toString());
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		sceneViewer.registerKeyInputs(s);
		stage.setScene(s);
		stage.show();
		stage.setTitle("Alchimist, Zay's Entity Editor");
		stage.setOnCloseRequest(e -> presenter.stopScene());
	}
	
	public void setComponentList(UserComponentList compList){
		presenter.setComponentList(compList);
	}
}
