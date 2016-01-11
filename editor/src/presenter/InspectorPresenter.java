package presenter;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simsilica.es.EntityComponent;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import presenter.common.EntityNode;
import util.LogUtil;
import view.InspectorTab;

public class InspectorPresenter {
	private ObjectProperty<EntityNode> selection = new SimpleObjectProperty<>();
	private InspectorTab view;
	
	ListChangeListener<EntityComponent> listener = e -> {
		while(e.next())
			if(e.wasReplaced())
				for(EntityComponent comp : e.getAddedSubList())
					view.updateComponentEditor(comp);
			else if(e.wasAdded())
				for(EntityComponent comp : e.getAddedSubList())
					view.addComponentEditor(comp);
			else if(e.wasRemoved())
				for(EntityComponent comp : e.getRemoved())
					view.removeComponentEditor(comp);
	};
	
	public InspectorPresenter(InspectorTab view) {
		this.view = view;
		selection.bind(EditorPlatform.getSelectionProperty());
		
		EditorPlatform.getSelectionProperty().addListener((observable, oldValue, newValue) -> {
			view.inspectNewEntity(newValue);
			if(oldValue != null)
				oldValue.componentListProperty().removeListener(listener);
			if(newValue != null)
				newValue.componentListProperty().addListener(listener);
		});
	}
	
	public void updateComponent(EntityComponent comp, String propertyName, Object value){
		// In this piece of code, we can't just change the component's field because it's imutable
		// we serialize the whole component into a jsontree, change the value in the tree, then
		// deserialize in a new component, that we will be able to attach to entity the proper way
		ObjectMapper mapper = new ObjectMapper();
		JsonNode n = mapper.valueToTree(EditorPlatform.getEntityData().getComponent(selection.getValue().getEntityId(), comp.getClass()));
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
			
		EditorPlatform.getEntityData().setComponent(selection.getValue().getEntityId(), newComp);
	}
	
	public void addComponent(String componentName){
		try {
			EntityComponent comp = EditorPlatform.getUserComponentList().getValue().get(componentName).newInstance();
			EditorPlatform.getEntityData().setComponent(selection.getValue().getEntityId(), comp);
		} catch (InstantiationException | IllegalAccessException e) {
			LogUtil.warning("Can't instanciate component "+componentName);
		}
	}
	public void removeComponent(Class<? extends EntityComponent> componentClass){
		EntityComponent comp = null;
		for(EntityComponent c : selection.getValue().componentListProperty())
			if(c.getClass() == componentClass){
				comp = c;
				break;
			}
		if(comp == null)
			throw new IllegalArgumentException("trying to remove a component ("+componentClass.getSimpleName()+") that doesn't exist on current entity");
		selection.getValue().componentListProperty().remove(comp);
		EditorPlatform.getEntityData().removeComponent(selection.getValue().getEntityId(), componentClass);
	}
	
	public List<String> getComponentNames(){
		return EditorPlatform.getUserComponentList().getValue().getSortedNames();
	}
}
