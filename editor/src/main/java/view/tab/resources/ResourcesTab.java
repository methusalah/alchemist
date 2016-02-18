package main.java.view.tab.resources;

import main.java.view.control.OverviewTab;
import main.java.view.util.ViewLoader;

public class ResourcesTab extends OverviewTab {

	public ResourcesTab() {
		// This tab is created from a fxml as an exemple example.
		// It is so simple that we could have only add two lines of code.
    	ViewLoader.loadFXMLForControl(this);
	}
}
