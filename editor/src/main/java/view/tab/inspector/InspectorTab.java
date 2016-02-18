package main.java.view.tab.inspector;

import main.java.view.control.OverviewTab;

public class InspectorTab extends OverviewTab {

	public InspectorTab() {
		setText("Inspector");
		setContent(new Inspector());
	}
}
