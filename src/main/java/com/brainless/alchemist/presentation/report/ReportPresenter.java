package com.brainless.alchemist.presentation.report;

import com.brainless.alchemist.model.EditorPlatform;
import com.brainless.alchemist.model.tempImport.SceneGraphExplorer;
import com.brainless.alchemist.presentation.base.AbstractPresenter;

public class ReportPresenter extends AbstractPresenter<ReportViewer> {
	private final LogicThreadReporter logicThreadReporter = new LogicThreadReporter();
	private final SceneGraphReporter sceneGraphReporter = new SceneGraphReporter();

	public ReportPresenter(ReportViewer viewer) {
		super(viewer);
		EditorPlatform.getScene().enqueue((app) -> app.getStateManager().attach(new SceneGraphExplorer()));
	}
	
	public void setLogicThreadReporterEnable(boolean value){
		logicThreadReporter.setRefresh(value);
	}

	public void setSceneGraphReporterEnable(boolean value){
		sceneGraphReporter.setRefresh(value);
	}

	public LogicThreadReporter getLogicThreadReporter() {
		return logicThreadReporter;
	}

	public SceneGraphReporter getSceneGraphReporter() {
		return sceneGraphReporter;
	}
	
	
}
