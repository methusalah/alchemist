package presentation.report;

import java.text.DecimalFormat;

import com.jme3.app.SimpleApplication;
import com.jme3.post.Filter;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

import app.AppFacade;
import controller.SceneGraphReportNode;
import controller.SceneGraphReporter;
import controller.ECS.EntitySystem;
import controller.ECS.LogicLoop;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import model.ES.component.Naming;
import presentation.report.customControl.SceneGraphReportTreeView;
import presentation.util.ViewLoader;
import presenter.EditorPlatform;

public class Report extends BorderPane {

	@FXML
	private Label spatialLabel, entityCountLabel, idelingRatioLabel;
	
	@FXML
	private TitledPane sceneGraphPane, logicLoopPane;
	
	@FXML
	private BorderPane listViewContainer;
	
	public Report() {
		ViewLoader.loadFXMLForControl(this);

		// logic
		EditorPlatform.getScene().enqueue((app) -> app.getStateManager().attach(new SceneGraphReporter()));
		logicThreadReportRefresher.setCycleCount(Timeline.INDEFINITE);
		sceneGraphRefresher.setCycleCount(Timeline.INDEFINITE);
	}
	
	@FXML
	private void initialize(){
		SceneGraphReportTreeView report = new SceneGraphReportTreeView(sceneGraphReportRootNode);
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
				for(Filter f : AppFacade.getFilterPostProcessor().getFilterList())
					sb.append("    " + f + System.lineSeparator());
				
				spatialLabel.setText(sb.toString());
			}
		});
		report.setMaxSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
		listViewContainer.setCenter(report);
		
		sceneGraphPane.expandedProperty().addListener((observable, oldValue, newValue) -> setSceneGraphReportRefresh(newValue));
		logicLoopPane.expandedProperty().addListener((observable, oldValue, newValue) -> setLogicThreadReportRefresh(newValue));
		
		entityCount.addListener((observable, oldValue, newValue) -> entityCountLabel.setText("Entity count : " + newValue.toString()));
		DecimalFormat df = new DecimalFormat("0%");
		idelingRatio.addListener((observable, oldValue, newValue) -> idelingRatioLabel.setText("Idling rate : " + df.format(newValue)));

//		((Tab)getParent()).selectedProperty().addListener((observable, oldValue, newValue) -> {
//			presenter.setSceneGraphReportRefresh(newValue && sceneGraphPane.isExpanded());
//			presenter.setLogicThreadReportRefresh(newValue && logicLoopPane.isExpanded());
//		});
	}
	
	// PRESENTATION LOGIC
	
	private final ObjectProperty<SceneGraphReportNode> sceneGraphReportRootNode = new SimpleObjectProperty<>();
	private final IntegerProperty entityCount = new SimpleIntegerProperty();
	private final DoubleProperty idelingRatio = new SimpleDoubleProperty();

	Timeline logicThreadReportRefresher = new Timeline(new KeyFrame(Duration.millis(500), e -> {
		EditorPlatform.getScene().enqueue((app) -> refreshLogicThreadReport(app));
		entityCount.setValue(EditorPlatform.getEntityData().getEntities(Naming.class).size());
	}));

	private boolean refreshLogicThreadReport(SimpleApplication app) {
		EntitySystem es = app.getStateManager().getState(EntitySystem.class);
		double waitPerTick = (double)es.loop.getWaitTime() / es.loop.getTickCount();
		es.loop.resetIdleStats();
		Platform.runLater(() -> idelingRatio.setValue(waitPerTick/(LogicLoop.getMillisPerTick())));
		return true;
	}
	
	public void setLogicThreadReportRefresh(boolean value){
		if(value && logicThreadReportRefresher.getStatus() != Status.RUNNING)
			logicThreadReportRefresher.play();
		if(!value && logicThreadReportRefresher.getStatus() == Status.RUNNING)
			logicThreadReportRefresher.pause();
	}

	Timeline sceneGraphRefresher = new Timeline(new KeyFrame(Duration.millis(500), e -> {
		EditorPlatform.getScene().enqueue((app) -> refreshSceneGraphReport(app));
	}));

	private boolean refreshSceneGraphReport(SimpleApplication app) {
		SceneGraphReporter reporter = app.getStateManager().getState(SceneGraphReporter.class);
		SceneGraphReportNode rootNode = reporter.getReport();
		Platform.runLater(() -> {
			sceneGraphReportRootNode.setValue(rootNode);
		});
		return true;
	}

	public void setSceneGraphReportRefresh(boolean value){
		if(value && sceneGraphRefresher.getStatus() != Status.RUNNING)
			sceneGraphRefresher.play();
		if(!value && sceneGraphRefresher.getStatus() == Status.RUNNING)
			sceneGraphRefresher.pause();
	}

}
