package com.brainless.alchemist.presentation.hierarchy;

import com.brainless.alchemist.model.EditorPlatform;
import com.brainless.alchemist.model.ECS.blueprint.Blueprint;
import com.brainless.alchemist.model.ECS.builtInComponent.Naming;
import com.brainless.alchemist.model.ECS.builtInComponent.Parenting;
import com.brainless.alchemist.presentation.base.AbstractPresenter;
import com.brainless.alchemist.presentation.common.EntityNode;
import com.simsilica.es.EntityId;

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
