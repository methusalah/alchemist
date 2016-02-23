package model.ECS.builtInComponent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

/**
 * Built in component used to organize entities as a hierarchical tree.
 * Parenting is both used in the editor to create a visualisation of the entity relation,
 * and in the logic for an entity to know about its parent.
 * 
 * Note : Parenting is a unilateral link, from a child to its parent. It's discouraged to 
 * create links in the other way in an entity component system.
 * 
 * @author benoit
 *
 */
public class Parenting implements EntityComponent {
	private final EntityId parent;
	
	/**
	 * Default constructor, used only in the editor when adding an empty component.
	 */
	public Parenting() {
		parent = null;
	}
	
	public Parenting(@JsonProperty("parent")EntityId parent) {
		this.parent = parent;
	}

	public EntityId getParent() {
		return parent;
	}
}
