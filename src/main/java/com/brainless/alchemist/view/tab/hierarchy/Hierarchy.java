package com.brainless.alchemist.view.tab.hierarchy;

import com.brainless.alchemist.presentation.common.EntityNode;
import com.brainless.alchemist.presentation.hierarchy.HierarchyPresenter;
import com.brainless.alchemist.presentation.hierarchy.HierarchyViewer;
import com.brainless.alchemist.view.tab.hierarchy.customControl.EntityTreeView;
import com.brainless.alchemist.view.util.ViewLoader;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;

public class Hierarchy extends BorderPane implements HierarchyViewer {
	
	private final HierarchyPresenter presenter;
	private EntityTreeView tree;
	
	public Hierarchy() {
		presenter = new HierarchyPresenter(this);
		ViewLoader.loadFXMLForControl(this);
	}
	
	@FXML
	private void initialize(){
		tree = new EntityTreeView(presenter.getRootNode());
		tree.setMaxHeight(Double.MAX_VALUE);
		tree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue != null && newValue.getValue() != null)
				presenter.selectEntity(newValue.getValue());
		});
		tree.setOnCreateNewEntity((bp, en) -> presenter.createNewEntity(bp, en));
		tree.setOnUpdateParenting((child, parent) -> presenter.updateParenting(child, parent));
		setCenter(tree);
	}
	
	@FXML
	private void createNewEntity(){
		presenter.createNewEntity();
	}
	
	@FXML
	private void removeEntity(){
		presenter.removeEntity();
	}
	
	@Override
	public void updateSelection(EntityNode node){
		if(node == null)
			tree.getSelectionModel().clearSelection();
		else if(tree.getSelectionModel().isEmpty() || tree.getSelectionModel().getSelectedItem().getValue() != node)
			tree.getSelectionModel().select(findInTree(tree.getRoot(), node));
	}

	private static TreeItem<EntityNode> findInTree(TreeItem<EntityNode> parent, EntityNode newValue) {
		if(parent.getValue() == newValue)
			return parent;
		for(TreeItem<EntityNode> child : parent.getChildren()){
			TreeItem<EntityNode> found = findInTree(child, newValue);
			if(found != null)
				return found;
		}
		return null;
	}

	
}
