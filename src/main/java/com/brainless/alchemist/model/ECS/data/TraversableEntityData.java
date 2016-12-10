package com.brainless.alchemist.model.ECS.data;

import java.util.HashMap;
import java.util.Map;

import com.brainless.alchemist.model.ECS.builtInComponent.Naming;
import com.brainless.alchemist.model.ECS.builtInComponent.Parenting;
import com.brainless.alchemist.presentation.common.EntityNode;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

import javafx.application.Platform;
import util.LogUtil;

/***
 * A Specialized EntityData that maintain a tree of nodes representing the hierarchy of the entities and their components.
 * Used to observe all data changes and find entities by parenting relationship.
 * 
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
			if(!rootEntityNode.childrenListProperty().remove(findOrCreateNode(eid)))
				throw new RuntimeException("rootnode should have contained the removed entity node");
			entityNodes.remove(eid);
		});
	}
	
	/**
	 * Return the root node, that contains all entities that have no parent.
	 * @return
	 */
	public EntityNode getRootNode() {
		return rootEntityNode;
	}
	
	/**
	 * Return an entity node related to the given entity id.
	 * @param eid
	 * @return
	 */
	public EntityNode findOrCreateNode(EntityId eid){
		if(!entityNodes.containsKey(eid)){
			EntityNode ep = new EntityNode(eid, "Just created. Should not be seen.");
			rootEntityNode.childrenListProperty().add(ep);
			entityNodes.put(ep.getEntityId(), ep);
		}
		return entityNodes.get(eid);
	}
	
	private void removeNodeFromParent(EntityNode ep, Parenting parenting){
		if(parenting != null){
			EntityNode parentNode = findOrCreateNode(parenting.getParent());
			if(parentNode != null)
				parentNode.childrenListProperty().remove(ep);
		} else{
			rootEntityNode.childrenListProperty().remove(ep);
		}
	}
	
	private void handleComponentChange(EntityId eid, Class<? extends EntityComponent> compClass, EntityComponent lastComp, EntityComponent newComp){
		Platform.runLater(() -> {
			// special logic for parenting and naming
			if(compClass == Parenting.class){
				handleParentingChange(eid, (Parenting)lastComp, (Parenting)newComp);
			} else if(compClass == Naming.class){
				handleNamingChange(eid, (Naming)lastComp, (Naming)newComp);
			}
			
			EntityNode node = findOrCreateNode(eid);
			// here we have to add, remove, or set the new component to get the correct event for listeners
			// if we don't set, the listeners of the children list won't be able to understand that it's a component replacement.
			if(lastComp != null && newComp == null){
				// component is removed
				node.componentListProperty().remove(lastComp);
			} else if(lastComp != null && newComp != null){
				// component existed and is replaced
				int index = node.componentListProperty().indexOf(lastComp);
				if(index == -1){
					LogUtil.warning("Something went wrong with the known issue #16. We need to replace a component in an entity node, but the old component can't be found in the entity node's comp list.");
					LogUtil.warning("  Component class : " + compClass.getSimpleName());
					LogUtil.warning("  last component  : " + lastComp);
					LogUtil.warning("  new component   : " + newComp);
					Naming naming = getComponent(eid, Naming.class);
					LogUtil.warning("  entity          : " + eid + (naming != null? naming.getName() : "unamed."));
					LogUtil.warning("  please give info about that test case to https://github.com/brainless-studios/alchemist/issues/16");
				} else
					node.componentListProperty().set(index, newComp);
			} else if(lastComp == null && newComp != null){
				// component is added
				node.componentListProperty().add(newComp);
			}
		});
	}
	
	private void handleParentingChange(EntityId eid, Parenting lastParenting, Parenting newParenting){
		EntityNode node = findOrCreateNode(eid);
		removeNodeFromParent(node, lastParenting);
		if(newParenting != null){
			// The entity has a new parent. We register the entity in the new parent's presenter's children list
			EntityNode newParent = findOrCreateNode(newParenting.getParent());
			if(newParent == null) {
				LogUtil.warning("We try to set a new parent but we can't find the parent entity node.");
				LogUtil.warning("  last component  : " + lastParenting);
				LogUtil.warning("  new component   : " + newParenting);
				LogUtil.warning("  new parent      : " + newParenting.getParent() + " / name : " + (getComponent(newParenting.getParent(), Naming.class) != null? getComponent(newParenting.getParent(), Naming.class).getName() : "unnamed"));
				Naming naming = getComponent(eid, Naming.class);
				LogUtil.warning("  entity          : " + eid + (naming != null? naming.getName() : "unamed."));
			}
			newParent.childrenListProperty().add(node);
		} else
			rootEntityNode.childrenListProperty().add(node);
	}
	
	private void handleNamingChange(EntityId eid, Naming lastNaming, Naming newNaming){
		EntityNode node = findOrCreateNode(eid);
		node.nameProperty().setValue(newNaming == null? "Unnamed" : newNaming.getName());
	}

}
