package com.brainless.alchemist.presentation.report;

import com.brainless.alchemist.model.EditorPlatform;
import com.brainless.alchemist.model.ECS.builtInComponent.Naming;
import com.brainless.alchemist.model.ECS.pipeline.Pipeline;
import com.brainless.alchemist.model.ECS.pipeline.PipelineManager;
import com.jme3.app.SimpleApplication;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

public class LogicThreadReporter {
	private final IntegerProperty entityCount = new SimpleIntegerProperty();
	private final DoubleProperty idelingRatio = new SimpleDoubleProperty();
	private final Timeline worker = new Timeline(new KeyFrame(Duration.millis(500), e -> {
		EditorPlatform.getScene().enqueue((app) -> refreshLogicThreadReport(app));
		entityCount.setValue(EditorPlatform.getEntityData().getEntities(Naming.class).size());
	}));
	
	public LogicThreadReporter() {
		worker.setCycleCount(Animation.INDEFINITE);
	}

	private boolean refreshLogicThreadReport(SimpleApplication app) {
		PipelineManager manager = EditorPlatform.getPipelineManager();
		int count = 0;
		double idleTime = 0;
		for(Pipeline p : manager.getIndependantPipelines()){
			idleTime += (double)p.getWaitTime() / p.getTickCount();
			p.resetIdleStats();
			count++;
		}
		final double average = idleTime/count;
		Platform.runLater(() -> idelingRatio.setValue(average/(Pipeline.getMillisPerTick())));
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
