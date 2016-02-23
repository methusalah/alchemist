package commonLogic;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import model.ECS.blueprint.Blueprint;
import model.ECS.blueprint.BlueprintLibrary;

public class EntityInstance {

	private final String blueprintName;
	
	@JsonTypeInfo(use=Id.CLASS, include=As.PROPERTY, property="class")
	private final List<EntityComponent> components;
	
	private EntityId instanceId;
	
	public EntityInstance(@JsonProperty("blueprintName")String blueprintName,
			@JsonProperty("components")List<EntityComponent> components) {
		this.blueprintName = blueprintName;
		this.components = components;
	}

	@JsonIgnore
	public Blueprint getBlueprint() {
		return BlueprintLibrary.getBlueprint(blueprintName);
	}
	
	public String getBlueprintName(){
		return blueprintName;
	}

	public List<EntityComponent> getComponents() {
		return components;
	}

	public void instanciate(EntityData ed, EntityId parent) {
		if(!isInstanciated()){
			instanceId = getBlueprint().createEntity(ed, parent);
			for(EntityComponent comp : getComponents())
				ed.setComponent(instanceId, comp);
		}
	}

	public void uninstanciate(EntityData ed) {
		ed.removeEntity(instanceId);
		instanceId = null;
	}
	
	@JsonIgnore
	public boolean isInstanciated(){
		return instanceId != null;
	}
	
	
	
}
