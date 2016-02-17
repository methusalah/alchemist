package view.tab.scene;

import view.control.OverviewTab;

public class SceneTab extends OverviewTab {

	public SceneTab() {
		setText("Scene");
		setContent(new SceneView());
	}
}
