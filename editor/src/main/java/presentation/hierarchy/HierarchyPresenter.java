package main.java.presentation.hierarchy;

import com.simsilica.es.EntityId;

import main.java.model.EditorPlatform;
import main.java.model.ECS.blueprint.Blueprint;
import main.java.model.ECS.builtInComponent.Naming;
import main.java.model.ECS.builtInComponent.Parenting;
import main.java.presentation.EntityNode;
import main.java.presentation.base.AbstractPresenter;

public class HierarchyPresenter extends AbstractPresenter<HierarchyViewer> {

	public HierarchyPresenter(HierarchyViewer viewer) {
		super(viewer);
		EditorPlatform.getSelectionProperty().addListener((observable, oldValue, newValue) -> viewer.updateSelection(newValue));
	}
	
	public void createNewEntity(Blueprint bp, EntityNode parent){
		bp.createEntity(EditorPlatform.getEntityData(), parent == null? null : parent.getEntityId());
	}

	public void createNewEntity(){
		EntityId eid = EditorPlatform.getEntityData().createEntity();
		EditorPlatform.getEntityData().setComponent(eid, new Naming("Unamed entity"));
	}
	
	public void removeEntity(){
		EntityNode nodeToRemove = EditorPlatform.getSelectionProperty().getValue();
		for(EntityNode childNode : nodeToRemove.childrenListProperty()){
			EditorPlatform.getEntityData().removeEntity(childNode.getEntityId());
		}
		EditorPlatform.getEntityData().removeEntity(nodeToRemove.getEntityId());
	}
	
	public void selectEntity(EntityNode node){
		EditorPlatform.getSelectionProperty().set(node);
	}
	
	public void updateParenting(EntityNode child, EntityNode parent){
		EditorPlatform.getEntityData().setComponent(child.getEntityId(), new Parenting(parent.getEntityId()));
	}
	
	public EntityNode getRootNode(){
		return EditorPlatform.getEntityData().getRootNode();
	}



}
