package plugin.infiniteWorld.editor.view;

import com.brainless.alchemist.view.control.OverviewTab;

public class WorldEditorTab extends OverviewTab {

	public WorldEditorTab() {
		setText("World edition");
		setContent(new WorldEditor(this));
	}
}
