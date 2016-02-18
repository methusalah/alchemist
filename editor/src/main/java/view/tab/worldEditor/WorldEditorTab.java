package main.java.view.tab.worldEditor;

import main.java.view.control.OverviewTab;

public class WorldEditorTab extends OverviewTab {

	public WorldEditorTab() {
		setText("World edition");
		setContent(new WorldEditor(this));
	}
}
