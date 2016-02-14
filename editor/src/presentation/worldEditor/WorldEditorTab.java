package presentation.worldEditor;

import presentation.control.OverviewTab;

public class WorldEditorTab extends OverviewTab {

	public WorldEditorTab() {
		setText("World edition");
		setContent(new WorldEditor());
	}
}
