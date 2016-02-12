package presentation.inspector;

import presentation.commonControl.OverviewTab;

public class InspectorTab extends OverviewTab {

	public InspectorTab() {
		setText("Inspector");
		setContent(new Inspector());
	}
}
