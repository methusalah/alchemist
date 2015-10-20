package model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import model.ES.component.Naming;
import util.LogUtil;
import util.event.EntityRenamedEvent;
import util.event.EventManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

public class Inspector {
	private final EntityData entityData;
	private Map<String, Class<? extends EntityComponent>> componentClasses = new HashMap<>();
	
	private EntityId eid;
	private List<EntityComponent> comps;
	
	public Inspector(EntityData entityData) {
		this.entityData = entityData;
	}
	
	public void inspect(EntityId eid){
		this.eid = eid;
		comps = getScannableComponents(eid);
	}
	
	public List<EntityComponent> getScannableComponents(EntityId eid){
		List<EntityComponent> res = new ArrayList<>();
		for(Class<? extends EntityComponent> componentClass : componentClasses.values()){
			EntityComponent comp = entityData.getComponent(eid, componentClass);
			if(comp != null)
				res.add(comp);
		}
		return res;
	}
	
	public void updateComponent(EntityComponent comp, String propertyName, Object value){
		// In this piece of code, we can't just change the component's field because it's imutable
		// we serialize the whole component into a jsontree, change the value in the tree, then
		// deserialize in a new component, that we will be able to attach to entity the proper way
		ObjectMapper mapper = new ObjectMapper();
		JsonNode n = mapper.valueToTree(entityData.getComponent(eid, comp.getClass()));
		Iterator<Entry<String, JsonNode>> i = n.fields();
		while (i.hasNext()) {
			Entry<String, JsonNode> entry = (Entry<String, JsonNode>) i.next();
			if(entry.getKey().equals(propertyName)){
				entry.setValue(mapper.valueToTree(value));
				break;
			}
		}
		
		EntityComponent newComp = null;
		try {
			newComp = new ObjectMapper().treeToValue(n, comp.getClass());
			LogUtil.info("comp "+newComp);
			for(Field f : newComp.getClass().getFields()){
				try {
					LogUtil.info("    field "+f.getType()+" / "+ f.get(newComp));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	
			
		entityData.setComponent(eid, newComp);
		inspect(eid);
		if(comp instanceof Naming)
			EventManager.post(new EntityRenamedEvent(eid, value.toString()));
	}
	
	public void updateName(EntityId eid, String newName){
		entityData.setComponent(eid, new Naming(newName));
	}
	
	public void addComponentToScan(Class<? extends EntityComponent> compClass){
		componentClasses.put(compClass.getSimpleName(), compClass);
	}

	public List<String> getComponentNames(){
		List<String> res = new ArrayList<String>(componentClasses.keySet());
		Collections.sort(res);
		return res;
	}

	public List<EntityComponent> getComponents() {
		return comps;
	}

	public EntityId getEid() {
		return eid;
	}
	
	public void addComponent(String componentName){
		try {
			EntityComponent comp = componentClasses.get(componentName).newInstance();
			entityData.setComponent(eid, comp);
			comps.add(comp);
		} catch (InstantiationException | IllegalAccessException e) {
			LogUtil.warning("Can't instanciate component "+componentName);
		}
	}
	public void removeComponent(Class<? extends EntityComponent> componentClass){
		EntityComponent comp = null;
		for(EntityComponent c : comps)
			if(c.getClass() == componentClass){
				comp = c;
				break;
			}
		if(comp == null)
			throw new IllegalArgumentException("trying to remove a component ("+componentClass.getSimpleName()+") that doesn't exist on current entity");
		comps.remove(comp);
		entityData.removeComponent(eid, componentClass);
	}
}
