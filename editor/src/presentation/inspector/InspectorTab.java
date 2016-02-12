package presentation.inspector;

import presentation.control.OverviewTab;

public class InspectorTab extends OverviewTab {

	public InspectorTab() {
		setText("Inspector");
		setContent(new Inspector());
	}
}
