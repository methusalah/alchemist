package com.brainless.alchemist.view.tab.scene;

import com.brainless.alchemist.view.control.OverviewTab;

public class SceneViewTab extends OverviewTab {

	public SceneViewTab() {
		setText("Scene");
		setContent(new SceneView());
	}
}
