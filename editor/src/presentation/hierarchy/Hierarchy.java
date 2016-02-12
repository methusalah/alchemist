package presentation.hierarchy;

import com.simsilica.es.EntityId;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import model.ES.component.Naming;
import model.ES.component.Parenting;
import model.ES.serial.Blueprint;
import presentation.hierarchy.customControl.EntityTreeView;
import presentation.util.ViewLoader;
import presenter.EditorPlatform;
import presenter.common.EntityNode;

public class Hierarchy extends BorderPane {
	
	EntityTreeView tree;
	
	public Hierarchy() {
		ViewLoader.loadFXMLForControl(this);
	}
	
	@FXML
	private void initialize(){
		tree = new EntityTreeView(EditorPlatform.getEntityData().getRootNode());
		tree.setMaxHeight(Double.MAX_VALUE);
		tree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue != null && newValue.getValue() != null)
				EditorPlatform.getSelectionProperty().set(newValue.getValue());
		});
		tree.setOnCreateNewEntity((bp, en) -> createNewEntity(bp, en));
		tree.setOnUpdateParenting((child, parent) -> EditorPlatform.getEntityData().setComponent(child.getEntityId(), new Parenting(parent.getEntityId())));
		setCenter(tree);

	}
	
	@FXML
	private void createNewEntity(){
		EntityId eid = EditorPlatform.getEntityData().createEntity();
		EditorPlatform.getEntityData().setComponent(eid, new Naming("Unamed entity"));
	}

	public void createNewEntity(Blueprint bp, EntityNode parent){
		bp.createEntity(EditorPlatform.getEntityData(), parent == null? null : parent.getEntityId());
	}
	
	@FXML
	private void removeEntity(){
		EntityNode nodeToRemove = EditorPlatform.getSelectionProperty().getValue();
		for(EntityNode childNode : nodeToRemove.childrenListProperty()){
			EditorPlatform.getEntityData().removeEntity(childNode.getEntityId());
		}
		EditorPlatform.getEntityData().removeEntity(nodeToRemove.getEntityId());
	}
	
	public void clearSelection(){
		tree.getSelectionModel().clearSelection();
	}
	
	public void updateSelection(EntityNode node){
		if(tree.getSelectionModel().isEmpty() || tree.getSelectionModel().getSelectedItem().getValue() != node)
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
