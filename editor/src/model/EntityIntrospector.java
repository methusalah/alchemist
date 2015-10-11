package model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import util.LogUtil;
import util.event.ComponentPropertyChanged;
import util.event.EntitySelectionChanged;
import util.event.EventManager;
import view.InspectorView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.google.common.eventbus.Subscribe;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.sun.media.jfxmedia.logging.Logger;

public class EntityIntrospector {

	private final EntityData entityData;
	private List<Class<? extends EntityComponent>> componentClasses = new ArrayList<>();
	private final InspectorView view;
	
	private EntityId eid;
	private List<EntityComponent> comps;
	
	public EntityIntrospector(EntityData entityData, InspectorView view) {
		this.entityData = entityData;
		this.view = view;
		EventManager.register(this);
	}
	
	public void inspect(EntityId eid){
		this.eid = eid;
		comps = new ArrayList<>();
		for(Class<? extends EntityComponent> componentClass : componentClasses){
			EntityComponent comp = entityData.getComponent(eid, componentClass);
			if(comp != null)
				comps.add(comp);
		}
		view.loadComponents(comps);
	}
	
	@Subscribe
	public void changeEntity(EntitySelectionChanged event){
		inspect(event.eid);
	}
	

	
	@Subscribe
	public void updateComponent(ComponentPropertyChanged event){
		// In this piece of code, we can't just change the component's field because it's imutable
		// we serialize the whole component into a jsontree, change the value in the tree, then
		// deserialize in a new component, that we will be able to attach to entity the proper way
		ObjectMapper mapper = new ObjectMapper();
		JsonNode n = mapper.valueToTree(event.comp);
		Iterator<Entry<String, JsonNode>> i = n.fields();
		while (i.hasNext()) {
			Entry<String, JsonNode> entry = (Entry<String, JsonNode>) i.next();
			if(entry.getKey().equals(event.fieldName)){
				entry.setValue(mapper.valueToTree(event.newValue));
				break;
			}
		}
		
		EntityComponent comp = null;
		try {
			comp = new ObjectMapper().treeToValue(n, event.comp.getClass());
			LogUtil.info("comp "+comp);
			for(Field f : comp.getClass().getFields()){
				try {
					LogUtil.info("    field "+f.getType()+" / "+ f.get(comp));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	
			
//		entityData.setComponent(eid, comp);
	}
	
	public void addComponentToScan(Class<? extends EntityComponent> compClass){
		componentClasses.add(compClass);
	}
}
