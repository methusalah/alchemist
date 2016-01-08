package presenter;

import com.jme3.app.SimpleApplication;

import application.EditorPlatform;
import controller.SceneGraphReportNode;
import controller.SceneGraphReporter;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;
import util.LogUtil;

public class ReportPresenter {

	private ObjectProperty<SceneGraphReportNode> sceneGraphReportRootNode = new SimpleObjectProperty<>();
	
	Timeline sceneGraphRefresher = new Timeline(new KeyFrame(Duration.millis(500), e -> {
		EditorPlatform.getScene().enqueue((app) -> getSceneGraphReport(app, sceneGraphReportRootNode));
	}));
	
	public ReportPresenter() {
		EditorPlatform.getScene().enqueue((app) -> app.getStateManager().attach(new SceneGraphReporter()));
		sceneGraphRefresher.setCycleCount(Timeline.INDEFINITE);
	}
	
	private boolean getSceneGraphReport(SimpleApplication app, ObjectProperty<SceneGraphReportNode> sceneGraphReportRootNode) {
		SceneGraphReporter reporter = app.getStateManager().getState(SceneGraphReporter.class);
		SceneGraphReportNode rootNode = reporter.getReport();
		Platform.runLater(() -> {
			sceneGraphReportRootNode.setValue(rootNode);
		});
		return true;
	}

	public ObjectProperty<SceneGraphReportNode> getSceneGraphReportRootNode() {
		return sceneGraphReportRootNode;
	}
	
	public void setSceneGraphReportRefresh(boolean value){
		if(value && sceneGraphRefresher.getStatus() != Status.RUNNING)
			sceneGraphRefresher.play();
		if(!value && sceneGraphRefresher.getStatus() == Status.RUNNING)
			sceneGraphRefresher.pause();
	}

}
