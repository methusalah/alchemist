package presentation.resource;

import presentation.commonControl.OverviewTab;


public class ResourcesTab extends OverviewTab {
	
	public ResourcesTab() {
		setText("Resources");
		setContent(new Resources());
	}
	
}
