package view.tab.worldEditor;

import view.control.OverviewTab;

public class WorldEditorTab extends OverviewTab {

	public WorldEditorTab() {
		setText("World edition");
		setContent(new WorldEditor(this));
	}
}
