package model.ES.component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class Naming implements EntityComponent {
	public final String name;
	
	public Naming(@JsonProperty("name")String name) {
		this.name = name;
	}
}
