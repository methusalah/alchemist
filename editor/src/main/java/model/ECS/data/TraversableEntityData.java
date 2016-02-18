package main.java.model.ECS.data;

import java.util.HashMap;
import java.util.Map;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

import javafx.application.Platform;
import main.java.model.ECS.builtInComponent.Naming;
import main.java.model.ECS.builtInComponent.Parenting;
import main.java.presentation.EntityNode;

/***
 * A Sepacialized EntityData that maintain a tree of nodes representing the hierarchy of the entities and their components
 * 
 * Used to observe all data changes
 * @author benoit
 *
 */
public class TraversableEntityData extends SavableEntityData{
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
	public void removeEntity(EntityId eid) {
		super.removeEntity(eid);
		
		Platform.runLater(() -> {
			// after the entity has been removed, the node will necessarily be in the root node.
			if(!rootEntityNode.childrenListProperty().remove(getNode(eid)))
				throw new RuntimeException("rootnode should have contained the removed entity node");
			entityNodes.remove(eid);
		});
	}
	
	public EntityNode getRootNode() {
		return rootEntityNode;
	}
	
	public EntityNode getNode(EntityId eid){
		return entityNodes.get(eid);
	}
	
	private void removeNodeFromParent(EntityNode ep, Parenting parenting){
		if(parenting != null){
			EntityNode parentNode = getNode(parenting.getParent());
			if(parentNode != null)
				parentNode.childrenListProperty().remove(ep);
		} else{
			rootEntityNode.childrenListProperty().remove(ep);
		}
	}
	
	private void handleComponentChange(EntityId eid, Class<? extends EntityComponent> compClass, EntityComponent lastComp, EntityComponent newComp){
		Platform.runLater(() -> {
			if(!entityNodes.containsKey(eid)){
				EntityNode ep = new EntityNode(eid, "Just created. Should not be seen.");
				rootEntityNode.childrenListProperty().add(ep);
				entityNodes.put(ep.getEntityId(), ep);
			}
		});

		Platform.runLater(() -> {
			EntityNode node = getNode(eid);
			if(compClass == Parenting.class){
				Parenting parenting = (Parenting)newComp;
				removeNodeFromParent(node, (Parenting)lastComp);
				if(newComp != null){
					// The entity has a new parent. We register the entity in the new parent's presenter's children list
					EntityNode newParent = entityNodes.get(parenting.getParent());
					newParent.childrenListProperty().add(node);
				}
				else
					rootEntityNode.childrenListProperty().add(node);
			} else if(compClass == Naming.class){
				Naming naming = (Naming)newComp;
				node.nameProperty().setValue(newComp == null? "Unnamed" : naming.getName());
			}
			
			if(node != null){
				// we set the component instead of remove&add to get the correct event for listeners
				if(lastComp != null && newComp == null){
					// component is removed
					node.componentListProperty().remove(lastComp);
				} else if(lastComp != null && newComp != null){
					// component is replaced
					int index = node.componentListProperty().indexOf(lastComp);
					node.componentListProperty().set(index, newComp);
				} else if(lastComp == null && newComp != null){
					// component is added
					node.componentListProperty().add(newComp);
				}
			}
		});
	}
}
