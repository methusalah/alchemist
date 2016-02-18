package main.java.view.tab.resources;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import main.java.presentation.resources.ResourcesPresenter;
import main.java.presentation.resources.ResourcesViewer;
import main.java.view.tab.resources.customControl.BlueprintListView;
import main.java.view.util.ViewLoader;

public class Resources extends BorderPane implements ResourcesViewer {
	
	private final ResourcesPresenter presenter;
	
    public Resources() {
    	presenter = new ResourcesPresenter(this);
    	ViewLoader.loadFXMLForControl(this);
    }
    
	@FXML
	private void initialize() {
		BlueprintListView bpListView = new BlueprintListView();
		bpListView.itemsProperty().bind(presenter.getBlueprintList());
		bpListView.setOnSaveEntity(e -> presenter.saveEntityAsBlueprint(e));
		setCenter(bpListView);
		setAlignment(bpListView, Pos.CENTER);
	}
}
