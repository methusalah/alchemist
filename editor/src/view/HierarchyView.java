package view;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import model.EntityNode;
import util.event.EntitySelectionChanged;
import util.event.EventManager;

public class HierarchyView extends VBox{
	TreeView<EntityNode> tree;
	
	public HierarchyView() {
		setPrefWidth(300);
		Label title = new Label("Hierarchy");
		title.setMinHeight(40);
		title.setMaxWidth(Double.MAX_VALUE);
		title.setStyle("-fx-background-color: lightblue");
		getChildren().add(title);
		
		tree = new TreeView<>();
		getChildren().add(tree);
		tree.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<TreeItem<EntityNode>>() {

			@Override
			public void changed(ObservableValue<? extends TreeItem<EntityNode>> observable, TreeItem<EntityNode> oldValue, TreeItem<EntityNode> newValue) {
				if(newValue.getValue() != null)
					EventManager.post(new EntitySelectionChanged(newValue.getValue().parent));
			}
	      });
	}
	
	public void update(List<EntityNode> nodes){
		TreeItem<EntityNode> root = new TreeItem<EntityNode>();
		root.setExpanded(true);
		for(EntityNode n : nodes)
			addItem(root, n);
		tree.setRoot(root);
	}
	
	private void addItem(TreeItem<EntityNode> parentItem, EntityNode node){
		TreeItem<EntityNode> item = new TreeItem<EntityNode>(node);
		parentItem.getChildren().add(item);
		for(EntityNode childNode : node.children){
			addItem(item, childNode);
			
		}
	}

}
