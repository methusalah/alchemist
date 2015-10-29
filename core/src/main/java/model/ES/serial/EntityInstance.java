package model.ES.serial;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.simsilica.es.EntityComponent;

public class EntityInstance {

	private final Blueprint blueprint;
	@JsonTypeInfo(use=Id.CLASS, include=As.PROPERTY, property="class")
	private final List<EntityComponent> comps;
	
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
	
}
