package presentation.resources;

import presentation.commonControl.OverviewTab;
import presentation.util.ViewLoader;

public class ResourcesTab extends OverviewTab {

	public ResourcesTab() {
		// This tab is created from a fxml for example.
		// It is so simple that we could have only add two lines of code.
    	ViewLoader.loadFXMLForControl(this);
	}
}
