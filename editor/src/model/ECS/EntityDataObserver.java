package model.ECS;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import model.EntityPresenter;
import model.ECS.event.ComponentSetEvent;
import model.ECS.event.EntityAddedEvent;
import model.ECS.event.EntityRemovedEvent;
import model.ES.component.Naming;
import model.ES.component.hierarchy.Parenting;
import model.ES.serial.Blueprint;
import util.LogUtil;
import util.event.EventManager;

import com.google.common.eventbus.Subscribe;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.sun.swing.internal.plaf.synth.resources.synth;

public class EntityDataObserver {
	private final EntityData entityData;
	private final EntityPresenter rootEntityPresenter;
	private final Map<EntityId, EntityPresenter> presenters = new HashMap<>();

	public EntityDataObserver(EntityData entityData) {
		this.entityData = entityData;
		rootEntityPresenter = new EntityPresenter(null, "root");
		EventManager.register(this);
	}
	
	public EntityPresenter getRootEntityPresenter() {
		return rootEntityPresenter;
	}
	
	public EntityPresenter getPresenter(EntityId eid){
		return presenters.get(eid);
	}
	
	private void removePresenterFromParent(EntityPresenter ep, Parenting parenting){
		if(parenting != null){
			EntityPresenter parentPresenter = getPresenter(parenting.getParent());
			if(parentPresenter != null)
				parentPresenter.childrenListProperty().remove(ep);
		} else
			rootEntityPresenter.childrenListProperty().remove(ep);
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
				
				EntityPresenter ep = getPresenter(e.getEntityId());
				if(ep != null){
					// we set the component instead of remove&add to get the correct event for listeners
					if(e.getLastComp() != null && e.getNewComp() == null){
						// component is removed
						ep.componentListProperty().remove(e.getLastComp());
					} else if(e.getLastComp() != null && e.getNewComp() != null){
						// component is replaced
						int index = ep.componentListProperty().indexOf(e.getLastComp());
						ep.componentListProperty().set(index, e.getNewComp());
					} else if(e.getLastComp() == null && e.getNewComp() != null){
						// component is added
						ep.componentListProperty().add(e.getNewComp());
					}
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
	
	public void changeSelectedEntity(EntityId eid){
		
	}

}
