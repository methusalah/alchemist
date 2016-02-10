package presentation.hierarchy;

import presentation.commonControl.OverviewTab;

public class HierarchyTab extends OverviewTab {

	public HierarchyTab() {
		setText("Hierarchy");
		setContent(new Hierarchy());
	}
}
