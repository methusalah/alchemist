package model.ES.serial;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import model.ES.component.hierarchy.Parenting;

public class Blueprint {
	private final String name;

	@JsonTypeInfo(use=Id.CLASS, include=As.PROPERTY, property="class")
	private final List<EntityComponent> comps;
	private final List<Blueprint> children;
	
	public Blueprint(@JsonProperty("name")String name,
			@JsonProperty("comps")List<EntityComponent> comps,
			@JsonProperty("children")List<Blueprint> children) {
		this.name = name;
		this.comps = comps;
		this.children = children;
	}
	
	public String getName() {
		return name;
	}

	public List<EntityComponent> getComps() {
		return comps;
	}

	public List<Blueprint> getChildren() {
		return children;
	}
	
	public EntityId createEntity(EntityData ed, EntityId parent){
		EntityId res = ed.createEntity();
		for(EntityComponent comp : getComps()){
			ed.setComponent(res, comp);
		}
		if(parent != null)
			ed.setComponent(res, new Parenting(parent));
		
		for(Blueprint childBP : getChildren()){
			childBP.createEntity(ed, res);
		}
		return res;
	}
}
