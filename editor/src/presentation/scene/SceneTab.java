package presentation.scene;

import presentation.control.OverviewTab;

public class SceneTab extends OverviewTab {

	public SceneTab() {
		setText("Scene");
		setContent(new SceneView());
	}
}
