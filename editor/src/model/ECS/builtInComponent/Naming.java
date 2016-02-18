package model.ECS.builtInComponent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class Naming implements EntityComponent {
	private final String name;
	
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
