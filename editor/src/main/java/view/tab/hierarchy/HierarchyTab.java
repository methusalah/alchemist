package main.java.view.tab.hierarchy;

import main.java.view.control.OverviewTab;

public class HierarchyTab extends OverviewTab {

	public HierarchyTab() {
		setText("Hierarchy");
		setContent(new Hierarchy());
	}
}
