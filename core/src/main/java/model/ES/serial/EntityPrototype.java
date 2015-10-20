package model.ES.serial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import model.ES.component.hierarchy.Parenting;

public class EntityPrototype {
	public final String name;

	@JsonTypeInfo(use=Id.CLASS, include=As.PROPERTY, property="class")
	private final List<EntityComponent> comps;
	
	private final List<String> children;

	/**
	 * For deserialization purpose only.	
	 * @param name
	 * @param comps
	 * @param children
	 */
	public EntityPrototype(@JsonProperty("name")String name,
			@JsonProperty("comps")List<EntityComponent> comps,
			@JsonProperty("children")List<String> children) {
		this.name = name;
		this.comps = comps;
		this.children = children;
	}

	public EntityPrototype(String name) {
		this.name = name;
		comps = new ArrayList<>();
		children = new ArrayList<>();
	}
	
	public void add(EntityComponent comp){
		comps.add(comp);
	}
	
	public void add(String blueprintName){
		children.add(blueprintName);
	}
	
	public List<EntityComponent> getComps() {
		return Collections.unmodifiableList(comps);
	}

	public List<String> getChildren() {
		return Collections.unmodifiableList(children);
	}
	
	
}
