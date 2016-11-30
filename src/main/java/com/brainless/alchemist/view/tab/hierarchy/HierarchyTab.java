package com.brainless.alchemist.view.tab.hierarchy;

import com.brainless.alchemist.view.control.OverviewTab;

public class HierarchyTab extends OverviewTab {

	public HierarchyTab() {
		setText("Hierarchy");
		setContent(new Hierarchy());
	}
}
