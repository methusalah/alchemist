package presentation.resources;

import presentation.control.OverviewTab;
import presentation.util.ViewLoader;

public class ResourcesTab extends OverviewTab {

	public ResourcesTab() {
		// This tab is created from a fxml as an exemple example.
		// It is so simple that we could have only add two lines of code.
    	ViewLoader.loadFXMLForControl(this);
	}
}
