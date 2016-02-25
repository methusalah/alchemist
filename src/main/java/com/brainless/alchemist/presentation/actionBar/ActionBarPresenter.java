package com.brainless.alchemist.presentation.actionBar;

import com.brainless.alchemist.model.EditorPlatform;
import com.brainless.alchemist.model.ECS.data.EntityDataMemento;
import com.brainless.alchemist.model.ECS.pipeline.Pipeline;
import com.brainless.alchemist.presentation.actionBar.RunState.State;
import com.brainless.alchemist.presentation.base.AbstractPresenter;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ActionBarPresenter extends AbstractPresenter<ActionBarViewer> {
	private final IntegerProperty millisPerTickProperty = new SimpleIntegerProperty();
	private EntityDataMemento memento; 

	public ActionBarPresenter(ActionBarViewer viewer) {
		super(viewer);
		millisPerTickProperty.setValue(Pipeline.getMillisPerTick());
		millisPerTickProperty.addListener((observable, oldValue, newValue) -> Pipeline.setMillisPerTick(newValue.intValue()));
	}
	
	public void setRun(boolean value){
		if(value)
			run();
		else
			stop();
	}
	

	private void run(){
		memento = EditorPlatform.getEntityData().createMemento();

		viewer.setGameInputListener();

		EditorPlatform.getRunStateProperty().set(new RunState(State.Run));
		EditorPlatform.getScene().enqueue(app -> jmeRun(app));
	}

	private void stop(){
		viewer.removeGameInputlistener();
		// entity data restoring
		EditorPlatform.getRunStateProperty().set(new RunState(State.Stop));
		EditorPlatform.getScene().enqueue(app -> jmeStop(app));
	}

	public boolean jmeRun(SimpleApplication app){
		EditorPlatform.getPipelineManager().stopEditionPiplines();
		EditorPlatform.getPipelineManager().runPipelines();
		return true;
	}

	public boolean jmeStop(SimpleApplication app){
		EditorPlatform.getPipelineManager().stopPiplines();
		EditorPlatform.getPipelineManager().runEditionPiplines();
		
		// state has to be set in the jme thread, to ensure that no entity is added after cleanup
		EditorPlatform.getEntityData().setMemento(memento);
		return true;
	}

	public IntegerProperty getMillisPerTickProperty() {
		return millisPerTickProperty;
	}

}
