package presentation.report;

import model.EditorPlatform;
import model.tempImport.SceneGraphExplorer;
import presentation.base.AbstractPresenter;

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