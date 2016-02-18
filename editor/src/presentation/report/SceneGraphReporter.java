package presentation.report;

import com.jme3.app.SimpleApplication;

import controller.SceneGraphReportNode;
import controller.SceneGraphExplorer;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;
import model.EditorPlatform;

public class SceneGraphReporter {
	private final ObjectProperty<SceneGraphReportNode> sceneGraphReportRootNode = new SimpleObjectProperty<>();
	private final Timeline worker = new Timeline(new KeyFrame(Duration.millis(500), e -> {
		EditorPlatform.getScene().enqueue((app) -> refreshSceneGraphReport(app));
	}));
	
	public SceneGraphReporter() {
		worker.setCycleCount(Timeline.INDEFINITE);
	}

	private boolean refreshSceneGraphReport(SimpleApplication app) {
		SceneGraphExplorer reporter = app.getStateManager().getState(SceneGraphExplorer.class);
		SceneGraphReportNode rootNode = reporter.getReport();
		Platform.runLater(() -> sceneGraphReportRootNode.setValue(rootNode));
		return true;
	}

	public void setRefresh(boolean value){
		if(value && worker.getStatus() != Status.RUNNING)
			worker.play();
		if(!value && worker.getStatus() == Status.RUNNING)
			worker.pause();
	}

	public ObjectProperty<SceneGraphReportNode> getSceneGraphReportRootNode() {
		return sceneGraphReportRootNode;
	}
}
