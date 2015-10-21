package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import model.ES.component.Naming;
import util.LogUtil;
import util.event.EventManager;
import util.event.modelEvent.InspectionChangedEvent;

public class Inspector {
	private final EntityData entityData;
	private final Map<String, Class<? extends EntityComponent>> componentClasses = new HashMap<>();
	
	private EntityPresenter ep;
	
	public Inspector(EntityData entityData, ObjectProperty<EntityPresenter> selection) {
		this.entityData = entityData;
		ep = selection.getValue();
		selection.addListener(new ChangeListener<EntityPresenter>() {
			
			@Override
			public void changed(ObservableValue<? extends EntityPresenter> observable, EntityPresenter oldValue,
					EntityPresenter newValue) {
				ep = newValue;
				EventManager.post(new InspectionChangedEvent(newValue));
				inspect();
			}
		});
	}
	
	private void inspect(){
		List<EntityComponent> comps = new ArrayList<>();
		for(Class<? extends EntityComponent> componentClass : componentClasses.values()){
			EntityComponent comp = entityData.getComponent(ep.getEntityId(), componentClass);
			if(comp != null)
				comps.add(comp);
		}
		ep.componentListProperty().set(FXCollections.observableArrayList(comps));
	}
	
	public void updateComponent(EntityComponent comp, String propertyName, Object value){
		// In this piece of code, we can't just change the component's field because it's imutable
		// we serialize the whole component into a jsontree, change the value in the tree, then
		// deserialize in a new component, that we will be able to attach to entity the proper way
		ObjectMapper mapper = new ObjectMapper();
		JsonNode n = mapper.valueToTree(entityData.getComponent(ep.getEntityId(), comp.getClass()));
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
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
			
		entityData.setComponent(ep.getEntityId(), newComp);
		inspect();
		if(comp instanceof Naming){
			ep.nameProperty().setValue((String)value);
		}
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

	public void addComponent(String componentName){
		try {
			EntityComponent comp = componentClasses.get(componentName).newInstance();
			entityData.setComponent(ep.getEntityId(), comp);
			ep.componentListProperty().add(comp);
		} catch (InstantiationException | IllegalAccessException e) {
			LogUtil.warning("Can't instanciate component "+componentName);
		}
	}
	public void removeComponent(Class<? extends EntityComponent> componentClass){
		EntityComponent comp = null;
		for(EntityComponent c : ep.componentListProperty())
			if(c.getClass() == componentClass){
				comp = c;
				break;
			}
		if(comp == null)
			throw new IllegalArgumentException("trying to remove a component ("+componentClass.getSimpleName()+") that doesn't exist on current entity");
		ep.componentListProperty().remove(comp);
		entityData.removeComponent(ep.getEntityId(), componentClass);
	}

	public EntityPresenter getEntityPresenter() {
		return ep;
	}
}
