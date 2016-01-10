package presenter;

import com.jme3.app.SimpleApplication;

import application.EditorPlatform;
import controller.SceneGraphReportNode;
import controller.SceneGraphReporter;
import controller.ECS.DataState;
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
import javafx.util.Duration;
import model.ES.component.Naming;
import util.LogUtil;

public class ReportPresenter {

	private final ObjectProperty<SceneGraphReportNode> sceneGraphReportRootNode = new SimpleObjectProperty<>();
	private final IntegerProperty entityCount = new SimpleIntegerProperty();
	private final DoubleProperty idelingRatio = new SimpleDoubleProperty();
	
	public ReportPresenter() {
		EditorPlatform.getScene().enqueue((app) -> app.getStateManager().attach(new SceneGraphReporter()));
		logicThreadReportRefresher.setCycleCount(Timeline.INDEFINITE);
		sceneGraphRefresher.setCycleCount(Timeline.INDEFINITE);
	}
	
	Timeline logicThreadReportRefresher = new Timeline(new KeyFrame(Duration.millis(500), e -> {
		EditorPlatform.getScene().enqueue((app) -> refreshLogicThreadReport(app));
		entityCount.setValue(EditorPlatform.getEntityData().getEntities(Naming.class).size());
	}));

	private boolean refreshLogicThreadReport(SimpleApplication app) {
		EntitySystem es = app.getStateManager().getState(EntitySystem.class);
		double waitPerTick = (double)es.loop.getWaitTime() / es.loop.getTickCount();
		es.loop.resetIdleStats();
		Platform.runLater(() -> {
			idelingRatio.setValue(waitPerTick/(LogicLoop.TIME_PER_FRAME*1000));
		});
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

	public ObjectProperty<SceneGraphReportNode> getSceneGraphReportRootNode() {
		return sceneGraphReportRootNode;
	}

	public IntegerProperty getEntityCount() {
		return entityCount;
	}

	public DoubleProperty getIdelingRatio() {
		return idelingRatio;
	}
	

}
