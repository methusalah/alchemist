package view;

import application.EditorPlatform;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;
import presenter.EntityNode;
import presenter.HierarchyPresenter;
import view.controls.EntityTreeView;

public class HierarchyTab extends Tab {
	HierarchyPresenter presenter = new HierarchyPresenter(); 
	

	public HierarchyTab() {
		setText("Hierarchy");
		setClosable(false);
		
		VBox content = new VBox();
		content.setMinWidth(300);
		content.setMaxHeight(Double.MAX_VALUE);
		content.setPadding(new Insets(3));
		setContent(content);
		
		// tree
		EntityTreeView tree = new EntityTreeView(presenter.getRootNode());;
		
		// The hierarchy view can observe the current selection and change the selected tree item accordingly
		// Needed because the user can select an entity from the scene view
		EditorPlatform.getSelectionProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue == null)
				tree.getSelectionModel().clearSelection();
			else if(tree.getSelectionModel().isEmpty() || 
					tree.getSelectionModel().getSelectedItem().getValue() != newValue)
				tree.getSelectionModel().select(findInTree(tree.getRoot(), newValue));
		});
		tree.setMaxHeight(Double.MAX_VALUE);
		content.getChildren().add(tree);
		
		tree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue != null && newValue.getValue() != null)
				EditorPlatform.getSelectionProperty().set(newValue.getValue());
		});

		// add button
		Button btnAdd = new Button("Add entity");
		btnAdd.setOnAction(e -> presenter.createEntity());
		content.getChildren().add(btnAdd);

		// remove button
		Button btnRemove = new Button("Remove entity");
		btnRemove.setOnAction(e -> {
			if(!tree.getSelectionModel().isEmpty())
				presenter.removeEntity(tree.getSelectionModel().getSelectedItem().getValue());
		});
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
}
