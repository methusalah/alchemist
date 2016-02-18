package main.java.view.tab.scene;

import main.java.view.control.OverviewTab;

public class SceneViewTab extends OverviewTab {

	public SceneViewTab() {
		setText("Scene");
		setContent(new SceneView());
	}
}
