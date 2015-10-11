package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
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
import util.LogUtil;
import util.event.EntityCreationEvent;
import util.event.EntitySelectionChanged;
import util.event.EventManager;

public class HierarchyView extends VBox{
	TreeView<EntityNode> tree;
	private Map<EntityNode, Boolean> expandMemory = new HashMap<>();
	
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
	
	public void updateNames(){
		updateName(tree.getRoot());
	}
	
	private void updateName(TreeItem<EntityNode> node){
		EntityNode en = node.getValue();
		node.setValue(null);
		node.setValue(en);
		for(TreeItem<EntityNode> child : node.getChildren())
			updateName(child);
	}
	
	private void addItem(TreeItem<EntityNode> parentItem, EntityNode node){
		TreeItem<EntityNode> item = new TreeItem<EntityNode>(node);
		
		// We ask the item to register the expand value at event in a memory
		// to expand the tree in the same way next time it will be refresh
		item.valueProperty().addListener(new InvalidationListener() {
			
			@Override
			public void invalidated(Observable observable) {
				item.setValue(null);
				item.setValue((EntityNode)observable);
				LogUtil.info("handled");
			
			}
		});
		
		item.expandedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		        expandMemory.put(node, newValue);
		    }
		});
		if(!expandMemory.containsKey(node))
			expandMemory.put(node, false);
		item.setExpanded(expandMemory.get(node));
		
		parentItem.getChildren().add(item);
		for(EntityNode childNode : node.children){
			addItem(item, childNode);
			
		}
	}

}
