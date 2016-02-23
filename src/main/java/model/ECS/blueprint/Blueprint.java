package model.ECS.blueprint;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import model.ECS.builtInComponent.Parenting;

/**
 * Static and reproductible pattern to produce many entities from another one.
 * A blueprint holds all the components of an entity, and a list of other blueprints as children.
 * 
 * It is intended to produce an entity and all it's children recursively.
 * 
 * Note about mutable component : blueprint may produce entities that differs from the prototype
 * if the components has been modified in the meanwhile.
 * More specifically, the components are saved by reference at blueprint creation.
 * 
 * Blueprint are serialisable in JSon format.
 * 
 * @author benoit
 *
 */
public class Blueprint {
	private final String name;

	@JsonTypeInfo(use=Id.CLASS, include=As.PROPERTY, property="class")
	private final List<EntityComponent> components;
	
	private final List<Blueprint> children;
	
	/**
	 * Creates a new blueprint
	 * @param name the blueprint name
	 * @param comps the list of component
	 * @param children the list of child blueprints
	 */
	public Blueprint(@JsonProperty("name")String name,
			@JsonProperty("components")List<EntityComponent> components,
			@JsonProperty("children")List<Blueprint> children) {
		this.name = name;
		this.components = components;
		this.children = children;
	}
	
	public String getName() {
		return name;
	}

	public List<EntityComponent> getComponents() {
		return components;
	}

	public List<Blueprint> getChildren() {
		return children;
	}
	
	/**
	 * Create a new entity in the given entity data. Children entities are
	 * created from children blueprint as well, recursively.
	 * @param ed the entity data that will hold the entity
	 * @param parent the parent entity. If a parent is specified, a parenting component is added to the created entity. May be null. 
	 * @return the EntityID of the created entity.
	 */
	public EntityId createEntity(EntityData ed, EntityId parent){
		EntityId res = ed.createEntity();
		for(EntityComponent comp : getComponents()){
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
