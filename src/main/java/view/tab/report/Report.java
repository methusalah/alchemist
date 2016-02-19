package view.tab.report;

import com.jme3.post.Filter;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.NumberStringConverter;
import model.tempImport.RendererPlatform;
import presentation.report.ReportPresenter;
import presentation.report.ReportViewer;
import view.tab.report.customControl.SceneGraphReportTreeView;
import view.util.ViewLoader;

public class Report extends BorderPane implements ReportViewer {

	private final ReportPresenter presenter; 
	
	@FXML
	private Label spatialLabel, entityCountLabel, idelingRatioLabel;
	
	@FXML
	private TitledPane sceneGraphPane, logicLoopPane;
	
	@FXML
	private BorderPane listViewContainer;
	
	public Report() {
		presenter = new ReportPresenter(this);
		ViewLoader.loadFXMLForControl(this);
	}
	
	@FXML
	private void initialize(){
		SceneGraphReportTreeView report = new SceneGraphReportTreeView(presenter.getSceneGraphReporter().getSceneGraphReportRootNode());
		report.setMaxSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
		report.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue != null){
				Spatial s = newValue.getValue().getSpatial();
				StringBuilder sb = new StringBuilder(s.toString() + System.lineSeparator());
				sb.append("Translation : " + s.getWorldTranslation() + System.lineSeparator());
				sb.append("Rotation    : " + s.getWorldRotation() + System.lineSeparator());
				sb.append("Scale       : " + s.getWorldScale() + System.lineSeparator());
				sb.append("Shadow mode : " + s.getShadowMode() + System.lineSeparator());
				if(s instanceof Geometry){
					Geometry g = (Geometry)s;
					sb.append(" (geometry) mesh faces : " + g.getMesh().getTriangleCount() + System.lineSeparator());
				}
				sb.append("FilterPostProcessor : "+ System.lineSeparator());
				for(Filter f : RendererPlatform.getFilterPostProcessor().getFilterList())
					sb.append("    " + f + System.lineSeparator());
				
				spatialLabel.setText(sb.toString());
			}
		});
		listViewContainer.setCenter(report);
		
		sceneGraphPane.expandedProperty().addListener((observable, oldValue, newValue) -> presenter.setSceneGraphReporterEnable(newValue));
		logicLoopPane.expandedProperty().addListener((observable, oldValue, newValue) -> presenter.setLogicThreadReporterEnable(newValue));
		
		entityCountLabel.textProperty().bindBidirectional(presenter.getLogicThreadReporter().getEntityCount(), new NumberStringConverter("Entity count : 0"));
		idelingRatioLabel.textProperty().bindBidirectional(presenter.getLogicThreadReporter().getIdelingRatio(), new NumberStringConverter("Idling rate : 0%"));
	}
}
