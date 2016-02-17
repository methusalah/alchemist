package view.tab.inspector;

import view.control.OverviewTab;

public class InspectorTab extends OverviewTab {

	public InspectorTab() {
		setText("Inspector");
		setContent(new Inspector());
	}
}
