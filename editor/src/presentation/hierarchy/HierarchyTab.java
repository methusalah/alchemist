package presentation.hierarchy;

import presentation.control.OverviewTab;

public class HierarchyTab extends OverviewTab {

	public HierarchyTab() {
		setText("Hierarchy");
		setContent(new Hierarchy());
	}
}
