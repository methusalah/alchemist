package model.ES.component.world;

import model.world.World;

import com.simsilica.es.EntityComponent;

public class Scenery implements EntityComponent {
	public final World world;
	
	public Scenery(World world) {
		this.world = world;
	}
	
	
}
