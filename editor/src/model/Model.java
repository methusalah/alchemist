package model;

import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.google.common.eventbus.Subscribe;

import util.event.ComponentFieldChange;
import util.event.EventManager;
import util.event.MapResetEvent;

public class Model {

	public Model() {
		EventManager.register(this);
	}
	
	@Subscribe
	public void updateComponent(ComponentFieldChange event){
		// In this piece of code, we can't just change the component's field because it imutable
		// we serialize the whole component into a jsontree, change the value in the tree, then
		// deserialize in a new component, that we will be able to attach to entity the proper way
		ObjectMapper mapper = new ObjectMapper();
		JsonNode n = mapper.valueToTree(event.comp);
		Iterator<Entry<String, JsonNode>> i = n.fields();
		while (i.hasNext()) {
			Entry<String, JsonNode> entry = (Entry<String, JsonNode>) i.next();
			if(entry.getKey().equals(event.fieldName))
				entry.setValue(new JsonNodeFactory(false). objectNode(event.newValue));
		}
		
		try {
			comp =mapper.treeToValue(n, comp.getClass());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}
}
