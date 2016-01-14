package view;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.ES.component.motion.physic.CircleCollisionShape;
import presenter.EditorPlatform;
import presenter.OverviewPresenter;
import presenter.util.UserComponentList;
import view.instrument.circleCollisionShape.CircleCollisionShapeInstrument;
import view.instrument.planarStance.PlanarStanceInstrument;

public class Overview {
	private final OverviewPresenter presenter = new OverviewPresenter();
	
	public Overview(Stage stage) {
		SceneView sceneViewer = new SceneView();
		
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

		new PlanarStanceInstrument(EditorPlatform.getScene());
		//new CircleCollisionShapeInstrument(EditorPlatform.getScene());
		
		

		Scene s = new Scene(root);
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
