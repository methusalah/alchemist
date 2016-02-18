package presentation.report;

import com.jme3.app.SimpleApplication;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;
import model.EditorPlatform;
import model.ECS.builtInComponent.Naming;
import model.tempImport.EntitySystem;
import model.tempImport.LogicLoop;

public class LogicThreadReporter {
	private final IntegerProperty entityCount = new SimpleIntegerProperty();
	private final DoubleProperty idelingRatio = new SimpleDoubleProperty();
	private final Timeline worker = new Timeline(new KeyFrame(Duration.millis(500), e -> {
		EditorPlatform.getScene().enqueue((app) -> refreshLogicThreadReport(app));
		entityCount.setValue(EditorPlatform.getEntityData().getEntities(Naming.class).size());
	}));
	
	public LogicThreadReporter() {
		worker.setCycleCount(Timeline.INDEFINITE);
	}

	private boolean refreshLogicThreadReport(SimpleApplication app) {
		EntitySystem es = app.getStateManager().getState(EntitySystem.class);
		double waitPerTick = (double)es.loop.getWaitTime() / es.loop.getTickCount();
		es.loop.resetIdleStats();
		Platform.runLater(() -> idelingRatio.setValue(waitPerTick/(LogicLoop.getMillisPerTick())));
		return true;
	}

	public void setRefresh(boolean value){
		if(value && worker.getStatus() != Status.RUNNING)
			worker.play();
		if(!value && worker.getStatus() == Status.RUNNING)
			worker.pause();
	}
	
	public IntegerProperty getEntityCount() {
		return entityCount;
	}

	public DoubleProperty getIdelingRatio() {
		return idelingRatio;
	}


}
