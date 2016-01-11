package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;
import presenter.HierarchyPresenter;
import presenter.common.EntityNode;
import view.controls.EntityTreeView;

public class HierarchyTab extends Tab {
	private final HierarchyPresenter presenter; 
	
	EntityTreeView tree;
	
	public HierarchyTab() {
		presenter = new HierarchyPresenter(this);
		setText("Hierarchy");
		setClosable(false);
		
		VBox content = new VBox();
		content.setMinWidth(300);
		content.setPrefHeight(2000);
		content.setPadding(new Insets(3));
		setContent(content);
		
		// tree
		tree = new EntityTreeView(presenter);
		tree.setMaxHeight(Double.MAX_VALUE);
		content.getChildren().add(tree);
		tree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue != null && newValue.getValue() != null)
				presenter.select(newValue.getValue());
		});

		// add button
		Button btnAdd = new Button("Add entity");
		btnAdd.setOnAction(e -> presenter.createNewEntity());
		content.getChildren().add(btnAdd);

		// remove button
		Button btnRemove = new Button("Remove entity");
		btnRemove.setOnAction(e -> presenter.removeEntity());
		content.getChildren().add(btnRemove);
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
	
	public void clearSelection(){
		tree.getSelectionModel().clearSelection();
	}
	
	public void updateSelection(EntityNode node){
		if(tree.getSelectionModel().isEmpty() || tree.getSelectionModel().getSelectedItem().getValue() != node)
			tree.getSelectionModel().select(findInTree(tree.getRoot(), node));
	}
}
