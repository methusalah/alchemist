package model.ECS.builtInComponent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

/**
 * Built in component used to present an entity in the graphic user interface.
 * It is necessary for a human to identify entities in many places.
 * It should not be used for logic. Prefer the EntityID to identify an entity in code. 
 * 
 * @author benoit
 *
 */
public class Naming implements EntityComponent {
	private final String name;
	
	/**
	 * Default constructor, used only in the editor when adding an empty component.
	 */
	public Naming() {
		name = "Unnamed";
	}
	
	public Naming(@JsonProperty("name")String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
