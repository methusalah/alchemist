package view;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import model.EntityNode;
import util.event.EntityCreationEvent;
import util.event.EntitySelectionChanged;
import util.event.EventManager;
import view.controls.EntityNodeItem;

public class HierarchyView extends VBox{
	TreeView<EntityNode> tree;

	EntityNodeItem toSelect;

	
	public HierarchyView() {
		setPrefWidth(300);
		Label title = new Label("Hierarchy");
		title.setMinHeight(40);
		title.setMaxWidth(Double.MAX_VALUE);
		title.setStyle("-fx-background-color: lightblue");
		getChildren().add(title);
		
		Button btnAdd = new Button("Add entity");
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				EventManager.post(new EntityCreationEvent());
			}
		});
		
		getChildren().add(btnAdd);
	}
	
	public void update(List<EntityNode> nodes){
		getChildren().remove(tree);
		tree = new TreeView<>();
		getChildren().add(tree);
		tree.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<TreeItem<EntityNode>>() {

			@Override
			public void changed(ObservableValue<? extends TreeItem<EntityNode>> observable, TreeItem<EntityNode> oldValue, TreeItem<EntityNode> newValue) {
				if(newValue.getValue() != null)
					EventManager.post(new EntitySelectionChanged(newValue.getValue().parent));
				UIConfig.selectedEntityNode = newValue.getValue();
			}
		});
		EntityNodeItem root = new EntityNodeItem(null);
		root.setExpanded(true);
		toSelect = null;
		for(EntityNode n : nodes)
			addItem(root, n);
		tree.setRoot(root);
		if(toSelect != null)
			tree.getSelectionModel().select(toSelect);
	}
	
	private void addItem(EntityNodeItem parent, EntityNode node){
		EntityNodeItem i = new EntityNodeItem(node);
		parent.getChildren().add(i);
		if(node == UIConfig.selectedEntityNode)
			toSelect = i;
		for(EntityNode childNode : node.children){
			addItem(i, childNode);
		}
	}

}
