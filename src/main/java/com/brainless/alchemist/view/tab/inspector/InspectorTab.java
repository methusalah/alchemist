package com.brainless.alchemist.view.tab.inspector;

import com.brainless.alchemist.view.control.OverviewTab;

public class InspectorTab extends OverviewTab {

	public InspectorTab() {
		setText("Inspector");
		setContent(new Inspector());
	}
}
