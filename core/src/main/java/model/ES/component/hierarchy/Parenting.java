package model.ES.component.hierarchy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class Parenting implements EntityComponent {
	private final EntityId parent;
	
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
