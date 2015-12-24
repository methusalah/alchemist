package presenter;

import util.event.EntityCreationEvent;
import util.event.EntityDeletionEvent;
import util.event.EventManager;
import model.ES.component.Naming;
import model.ES.component.hierarchy.Parenting;
import model.ES.serial.Blueprint;
import application.EditorPlatform;

import com.simsilica.es.EntityId;

public class HierarchyPresenter {
	public void createNewEntity(){
		EntityId eid = EditorPlatform.getEntityData().createEntity();
		EditorPlatform.getEntityData().setComponent(eid, new Naming("Unamed entity"));
	}

	public void createNewEntity(Blueprint bp){
		bp.createEntity(EditorPlatform.getEntityData(), null);
	}
	
	public void removeEntity(EntityNode ep){
		for(EntityNode childNode : ep.childrenListProperty()){
			EditorPlatform.getEntityData().removeEntity(childNode.getEntityId());
		}
		EditorPlatform.getEntityData().removeEntity(ep.getEntityId());
	}
	
	public void updateParenting(EntityNode child, EntityNode parent){
		EditorPlatform.getEntityData().setComponent(child.getEntityId(), new Parenting(parent.getEntityId()));
	}
	
	public void createEntity(){
		EventManager.post(new EntityCreationEvent());
	}
	
	public void deleteEntity(EntityNode node){
		if(node != null)
			EventManager.post(new EntityDeletionEvent(node));
	}
	
	public EntityNode getRootNode(){
		return EditorPlatform.getObserver().getRootNode();
	}
	
}
