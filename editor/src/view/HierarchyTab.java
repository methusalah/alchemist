package view;

import java.util.List;

import com.simsilica.es.EntityId;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;
import model.EntityPresenter;
import util.event.EntityCreationEvent;
import util.event.EntityDeletionEvent;
import util.event.EventManager;
import view.controls.EntityTreeView;

public class HierarchyTab extends Tab {
	EntityTreeView tree;
	VBox content = new VBox();

	public HierarchyTab() {
		setText("Hierarchy");
		setClosable(false);
		setContent(content);
		content.setMinWidth(300);
		content.setMaxHeight(Double.MAX_VALUE);
		content.setPadding(new Insets(3));

		// add button
		Button btnAdd = new Button("Add entity");
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				EventManager.post(new EntityCreationEvent());
			}
		});
		content.getChildren().add(btnAdd);

		// remove button
		Button btnRemove = new Button("Remove entity");
		btnRemove.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(tree != null && tree.getSelectionModel().getSelectedItem() != null)
				EventManager.post(new EntityDeletionEvent(tree.getSelectionModel().getSelectedItem().getValue()));
			}
		});
		content.getChildren().add(btnRemove);
	}
	
	public void setRootPresenter(EntityPresenter root){
		content.getChildren().remove(tree);
		tree = new EntityTreeView(root);
		tree.setMaxHeight(Double.MAX_VALUE);
		content.getChildren().add(tree);
	}

	/*
	 * The hierarchy view can observe the current selection and change the selected tree item accordingly
	 * 
	 * Needed because the user can select an entity from the scene view
	 */
	public void setSelectionProperty(ObjectProperty<EntityPresenter> selection){
		selection.addListener(new ChangeListener<EntityPresenter>() {

			@Override
			public void changed(ObservableValue<? extends EntityPresenter> observable, EntityPresenter oldValue, EntityPresenter newValue) {
				if(newValue == null)
					tree.getSelectionModel().clearSelection();
				else if(tree.getSelectionModel().getSelectedItem() == null || 
						tree.getSelectionModel().getSelectedItem().getValue() != newValue)
					tree.getSelectionModel().select(findInTree(tree.getRoot(), newValue));
			}

			private TreeItem<EntityPresenter> findInTree(TreeItem<EntityPresenter> parent, EntityPresenter newValue) {
				if(parent.getValue() == newValue)
					return parent;
				for(TreeItem<EntityPresenter> child : parent.getChildren()){
					TreeItem<EntityPresenter> found = findInTree(child, newValue);
					if(found != null)
						return found;
				}
				return null;
			}
		});
	}
}
