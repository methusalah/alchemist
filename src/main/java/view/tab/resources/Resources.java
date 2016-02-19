package view.tab.resources;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import presentation.resources.ResourcesPresenter;
import presentation.resources.ResourcesViewer;
import view.tab.resources.customControl.BlueprintListView;
import view.util.ViewLoader;

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
