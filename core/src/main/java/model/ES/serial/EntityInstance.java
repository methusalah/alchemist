package model.ES.serial;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

public class EntityInstance {

	private final Blueprint blueprint;
	@JsonTypeInfo(use=Id.CLASS, include=As.PROPERTY, property="class")
	private final List<EntityComponent> comps;
	
	private EntityId instanceId;
	
	public EntityInstance(@JsonProperty("blueprint")Blueprint blueprint,
			@JsonProperty("comps")List<EntityComponent> comps) {
		this.blueprint = blueprint;
		this.comps = comps;
	}

	public Blueprint getBlueprint() {
		return blueprint;
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
	
	public boolean isInstanciated(){
		return instanceId != null;
	}
	
	
	
}
