package model.ES.component.world;

import com.simsilica.es.EntityComponent;

public class World implements EntityComponent {
	private final String name;
	
	public World() {
		name = "unnamed";
	}
	
	public World(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
