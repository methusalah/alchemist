package model.ECS;

import java.util.HashMap;
import java.util.Map;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;
import com.simsilica.es.base.DefaultEntityData;

import javafx.application.Platform;
import model.ES.component.Naming;
import model.ES.component.hierarchy.Parenting;
import presenter.EntityNode;

public class TraversableEntityData extends DefaultEntityData{
	private final EntityNode rootEntityNode;
	private final Map<EntityId, EntityNode> entityNodes = new HashMap<>();

	public TraversableEntityData() {
		rootEntityNode = new EntityNode(null, "root");
	}
	
	@Override
	public void setComponent(EntityId entityId, EntityComponent component) {
		EntityComponent lastComp = getComponent(entityId, component.getClass());
		super.setComponent(entityId, component);
		handleComponentChange(entityId, component.getClass(), lastComp, component);
	}
	
	@Override
	public boolean removeComponent(EntityId entityId, Class type) {
		EntityComponent lastComp = getComponent(entityId, type);
		handleComponentChange(entityId, type, lastComp, null);
		return super.removeComponent(entityId, type);
	}

	@Override
	public EntityId createEntity() {
		EntityId res = super.createEntity();
		Platform.runLater(() -> {
			EntityNode ep = new EntityNode(res, "Just created. Should not be seen.");
			rootEntityNode.childrenListProperty().add(ep);
			entityNodes.put(ep.getEntityId(), ep);
		});
		return res;
	}
	
	@Override
	public void removeEntity(EntityId eid) {
		super.removeEntity(eid);
		Platform.runLater(() -> {
			Parenting parenting = getComponent(eid, Parenting.class);
			removePresenterFromParent(getNode(eid), parenting);
			entityNodes.remove(eid);
		});
	}
	
	public EntityNode getRootNode() {
		return rootEntityNode;
	}
	
	public EntityNode getNode(EntityId eid){
		return entityNodes.get(eid);
	}
	
	private void removePresenterFromParent(EntityNode ep, Parenting parenting){
		if(parenting != null){
			EntityNode parentPresenter = getNode(parenting.getParent());
			if(parentPresenter != null)
				parentPresenter.childrenListProperty().remove(ep);
		} else
			rootEntityNode.childrenListProperty().remove(ep);
	}
	
	private void handleComponentChange(EntityId eid, Class<? extends EntityComponent> compClass, EntityComponent lastComp, EntityComponent newComp){
		Platform.runLater(() -> {
				if(compClass == Parenting.class){
					removePresenterFromParent(getNode(eid), (Parenting)lastComp);
					if(newComp != null){
						// The entity has a new parent. We register the entity in the new parent's presenter's children list
						EntityNode newParent = entityNodes.get(((Parenting)newComp).getParent());
						newParent.childrenListProperty().add(getNode(eid));
					}
				} else if(compClass == Naming.class){
					if(newComp != null)
						getNode(eid).nameProperty().setValue(((Naming)newComp).getName());
				}
				
				EntityNode ep = getNode(eid);
				if(ep != null){
					// we set the component instead of remove&add to get the correct event for listeners
					if(lastComp != null && newComp == null){
						// component is removed
						ep.componentListProperty().remove(newComp);
					} else if(lastComp != null && newComp != null){
						// component is replaced
						int index = ep.componentListProperty().indexOf(lastComp);
						ep.componentListProperty().set(index, newComp);
					} else if(lastComp == null && newComp != null){
						// component is added
						ep.componentListProperty().add(newComp);
					}
				}
		});
	}
}
