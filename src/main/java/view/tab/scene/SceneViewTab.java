package view.tab.scene;

import view.control.OverviewTab;

public class SceneViewTab extends OverviewTab {

	public SceneViewTab() {
		setText("Scene");
		setContent(new SceneView());
	}
}
