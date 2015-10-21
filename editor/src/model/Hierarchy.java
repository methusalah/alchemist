package model;

import java.util.HashMap;
import java.util.Map;

import model.ES.component.Naming;
import model.ES.component.hierarchy.Parenting;
import util.LogUtil;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

public class Hierarchy {
	private final EntityData entityData;
	private final EntityPresenter rootEntityPresenter;
	private final Map<EntityId, EntityPresenter> presenters = new HashMap<>();

	public Hierarchy(EntityData entityData) {
		this.entityData = entityData;
		rootEntityPresenter = new EntityPresenter(null, "root");
		createEntityHierarchy();
	}
	
	public EntityPresenter getRootEntityPresenter() {
		return rootEntityPresenter;
	}

	public void createNewEntity(){
		String name = "Unamed entity";
		// update entityData
		EntityId newEntityId = entityData.createEntity();
		entityData.setComponent(newEntityId, new Naming(name));

		//update presenters
		EntityPresenter ep = new EntityPresenter(newEntityId, name);
		rootEntityPresenter.childrenListProperty().add(ep);
		presenters.put(newEntityId, ep);
	}
	
	public void removeEntity(EntityId eid){
		LogUtil.info("deletion");
		// update presenters		
		removePresenterFromParent(getPresenter(eid));
		
		// update entityData
		entityData.removeEntity(eid);
		for(EntityPresenter childNode : getPresenter(eid).childrenListProperty()){
			entityData.removeEntity(childNode.getEntityId());
		}
	}
	
	public void updateParenting(EntityId eid, EntityId parentid){
		EntityPresenter ep = getPresenter(eid);
		// update presenters
		removePresenterFromParent(ep);
		getPresenter(parentid).childrenListProperty().add(ep);

		// update entityData
		entityData.setComponent(eid, new Parenting(parentid));
	}
	
	public EntityPresenter getPresenter(EntityId eid){
		return presenters.get(eid);
	}
	
	private void removePresenterFromParent(EntityPresenter ep){
		if(ep != rootEntityPresenter){
			Parenting parenting = entityData.getComponent(ep.getEntityId(), Parenting.class);
			if(parenting != null){
				getPresenter(parenting.getParent()).childrenListProperty().remove(ep);
			}
		}
	}
	
	private void createEntityHierarchy(){
		presenters.clear();
		EntitySet set = entityData.getEntities(Naming.class);
		for(Entity e : set){
			addEntity(e.getId());
		}
		for(Entity e : set){
			linkEntity(e.getId());
		}
	}
	
	private void addEntity(EntityId eid){
		Naming naming = entityData.getComponent(eid, Naming.class);
		EntityPresenter n = new EntityPresenter(eid, naming.name);
		presenters.put(eid, n);
	}
	
	private void linkEntity(EntityId eid){
		EntityPresenter n = presenters.get(eid);

		Parenting parenting = entityData.getComponent(eid, Parenting.class);
		EntityPresenter parent = parenting == null? rootEntityPresenter : presenters.get(parenting.getParent());
		parent.childrenListProperty().add(n);
	}
}
