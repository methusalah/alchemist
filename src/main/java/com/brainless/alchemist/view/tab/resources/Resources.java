package com.brainless.alchemist.view.tab.resources;

import com.brainless.alchemist.presentation.resources.ResourcesPresenter;
import com.brainless.alchemist.presentation.resources.ResourcesViewer;
import com.brainless.alchemist.view.tab.resources.customControl.BlueprintListView;
import com.brainless.alchemist.view.util.ViewLoader;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;

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
