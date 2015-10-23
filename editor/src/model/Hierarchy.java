package model;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import model.ECS.event.ComponentSetEvent;
import model.ECS.event.EntityAddedEvent;
import model.ECS.event.EntityRemovedEvent;
import model.ES.component.Naming;
import model.ES.component.hierarchy.Parenting;
import util.event.EventManager;

import com.google.common.eventbus.Subscribe;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.sun.swing.internal.plaf.synth.resources.synth;

public class Hierarchy {
	private final EntityData entityData;
	private final EntityPresenter rootEntityPresenter;
	private final Map<EntityId, EntityPresenter> presenters = new HashMap<>();

	public Hierarchy(EntityData entityData) {
		this.entityData = entityData;
		rootEntityPresenter = new EntityPresenter(null, "root");
		EventManager.register(this);
	}
	
	public EntityPresenter getRootEntityPresenter() {
		return rootEntityPresenter;
	}

	public void createNewEntity(){
		EntityId eid = entityData.createEntity();
		entityData.setComponent(eid, new Naming("Unamed entity"));
	}

	public void createNewEntity(Blueprint bp){
		bp.createEntity(entityData, null);
	}
	
	public void removeEntity(EntityPresenter ep){
		for(EntityPresenter childNode : ep.childrenListProperty()){
			entityData.removeEntity(childNode.getEntityId());
		}
		entityData.removeEntity(ep.getEntityId());
	}
	
	public void updateParenting(EntityPresenter child, EntityPresenter parent){
		entityData.setComponent(child.getEntityId(), new Parenting(parent.getEntityId()));
	}
	
	public EntityPresenter getPresenter(EntityId eid){
		return presenters.get(eid);
	}
	
//	private void createEntityHierarchy(){
//		presenters.clear();
//		EntitySet set = entityData.getEntities(Naming.class);
//		for(Entity e : set){
//			addEntity(e.getId());
//		}
//		for(Entity e : set){
//			linkEntity(e.getId());
//		}
//	}
//	
//	private void addEntity(EntityId eid){
//		Naming naming = entityData.getComponent(eid, Naming.class);
//		EntityPresenter n = new EntityPresenter(eid, naming.name);
//		presenters.put(eid, n);
//	}
//	
//	private void linkEntity(EntityId eid){
//		EntityPresenter n = presenters.get(eid);
//
//		Parenting parenting = entityData.getComponent(eid, Parenting.class);
//		EntityPresenter parent = parenting == null? rootEntityPresenter : presenters.get(parenting.getParent());
//		parent.childrenListProperty().add(n);
//	}

	private void removePresenterFromParent(EntityPresenter ep, Parenting parenting){
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				if(parenting != null){
					EntityPresenter parentPresenter = getPresenter(parenting.getParent());
					if(parentPresenter != null)
						parentPresenter.childrenListProperty().remove(ep);
				} else
					rootEntityPresenter.childrenListProperty().remove(ep);
			}
		});
	}
	
	@Subscribe
	public void handleComponentSetEvent(ComponentSetEvent e){
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				if(e.getCompClass() == Parenting.class){
					removePresenterFromParent(getPresenter(e.getEntityId()), (Parenting)e.getLastComp());
					if(e.getNewComp() != null){
						// The entity has a new parent. We register the entity in the new parent's presenter's children list
						EntityPresenter newParent = presenters.get(((Parenting)e.getNewComp()).getParent());
						newParent.childrenListProperty().add(getPresenter(e.getEntityId()));
					}
				} else if(e.getCompClass() == Naming.class){
					if(e.getNewComp() != null)
						getPresenter(e.getEntityId()).nameProperty().setValue(((Naming)e.getNewComp()).getName());
				}
			}
		});
	}

	@Subscribe
	public void handleEntityAddedEvent(EntityAddedEvent e){
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				EntityPresenter ep = new EntityPresenter(e.getEntityId(), "Just created. Should not be seen.");
				rootEntityPresenter.childrenListProperty().add(ep);
				presenters.put(ep.getEntityId(), ep);
			}
		});
	}

	@Subscribe
	public void handleEntityRemovedEvent(EntityRemovedEvent e){
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				Parenting parenting = entityData.getComponent(e.getEntityId(), Parenting.class);
				removePresenterFromParent(getPresenter(e.getEntityId()), parenting);
				presenters.remove(e.getEntityId());
			}
		});
	}

}
