package presenter;

import com.simsilica.es.EntityId;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import model.ES.component.Naming;
import model.ES.component.hierarchy.Parenting;
import model.ES.serial.Blueprint;
import presenter.common.EntityNode;
import view.HierarchyTab;

public class HierarchyPresenter {
	
	public HierarchyPresenter(HierarchyTab view) {
		EditorPlatform.getSelectionProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue == null)
				view.clearSelection();
			else
				view.updateSelection(newValue);
		});
	}
	
	public void select(EntityNode node){
		EditorPlatform.getSelectionProperty().set(node);
	}
	
	public void createNewEntity(){
		EntityId eid = EditorPlatform.getEntityData().createEntity();
		EditorPlatform.getEntityData().setComponent(eid, new Naming("Unamed entity"));
	}

	public void createNewEntity(Blueprint bp, EntityNode parent){
		bp.createEntity(EditorPlatform.getEntityData(), parent == null? null : parent.getEntityId());
	}
	
	public void removeEntity(){
		EntityNode nodeToRemove = EditorPlatform.getSelectionProperty().getValue();
		for(EntityNode childNode : nodeToRemove.childrenListProperty()){
			EditorPlatform.getEntityData().removeEntity(childNode.getEntityId());
		}
		EditorPlatform.getEntityData().removeEntity(nodeToRemove.getEntityId());
	}
	
	public void updateParenting(EntityNode child, EntityNode parent){
		EditorPlatform.getEntityData().setComponent(child.getEntityId(), new Parenting(parent.getEntityId()));
	}
	
	public EntityNode getRootNode(){
		return EditorPlatform.getEntityData().getRootNode();
	}
}
