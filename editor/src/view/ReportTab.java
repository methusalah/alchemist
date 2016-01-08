package view;


import com.jme3.scene.Spatial;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import presenter.ReportPresenter;
import view.controls.SceneGraphReportTreeView;

public class ReportTab extends Tab {
	ReportPresenter presenter = new ReportPresenter();
	
	public ReportTab() {
		setText("Report");
		setClosable(false);
		
		VBox content = new VBox();
		setContent(content);
		
		Label l = new Label();
		SceneGraphReportTreeView report = new SceneGraphReportTreeView(presenter.getSceneGraphReportRootNode());
		report.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue != null){
				Spatial s = newValue.getValue().getSpatial();
				StringBuilder sb = new StringBuilder(s.toString() + System.lineSeparator());
				sb.append("Translation : " + s.getWorldTranslation() + System.lineSeparator());
				sb.append("Rotation    : " + s.getWorldRotation() + System.lineSeparator());
				sb.append("Scale       : " + s.getWorldScale() + System.lineSeparator());
				sb.append("Shadow mode : " + s.getShadowMode() + System.lineSeparator());
				
				l.setText(sb.toString());
			}
		});
		TitledPane sceneGraph = new TitledPane("Scene Graph", new VBox(report, l));		
		sceneGraph.setExpanded(false);
		sceneGraph.expandedProperty().addListener((observable, oldValue, newValue) -> presenter.setSceneGraphReportRefresh(newValue));
		content.getChildren().add(sceneGraph);
		
		selectedProperty().addListener((observable, oldValue, newValue) -> {
			presenter.setSceneGraphReportRefresh(newValue && sceneGraph.isExpanded());
		});

		
		
	}
}
