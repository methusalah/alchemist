package presentation.scene;

import presentation.commonControl.OverviewTab;

public class SceneTab extends OverviewTab {

	public SceneTab() {
		setText("Scene");
		setContent(new SceneView());
	}
}
