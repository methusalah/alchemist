package view.tab.hierarchy;

import view.control.OverviewTab;

public class HierarchyTab extends OverviewTab {

	public HierarchyTab() {
		setText("Hierarchy");
		setContent(new Hierarchy());
	}
}
