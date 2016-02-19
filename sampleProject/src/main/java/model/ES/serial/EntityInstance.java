package model.ES.serial;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import model.tempImport.Blueprint;
import model.tempImport.BlueprintLibrary;

public class EntityInstance {

	private final String blueprintName;
	
	@JsonTypeInfo(use=Id.CLASS, include=As.PROPERTY, property="class")
	private final List<EntityComponent> comps;
	
	private EntityId instanceId;
	
	public EntityInstance(@JsonProperty("blueprintName")String blueprintName,
			@JsonProperty("comps")List<EntityComponent> comps) {
		this.blueprintName = blueprintName;
		this.comps = comps;
	}

	@JsonIgnore
	public Blueprint getBlueprint() {
		return BlueprintLibrary.getBlueprint(blueprintName);
	}
	
	public String getBlueprintName(){
		return blueprintName;
	}

	public List<EntityComponent> getComps() {
		return comps;
	}

	public void instanciate(EntityData ed, EntityId parent) {
		if(!isInstanciated()){
			instanceId = getBlueprint().createEntity(ed, parent);
			for(EntityComponent comp : getComps())
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
