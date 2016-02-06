package model.ES.component.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class Spawning implements EntityComponent {
	private final String blueprintName;
	private final int toSpawn;
	private final int toSpawnRange;
	
	public Spawning(){
		blueprintName = "";
		toSpawn = 0;
		toSpawnRange = 0;
	}
	
	public Spawning(@JsonProperty("blueprintName")String blueprintName,
			@JsonProperty("toSpawn")int toSpawn,
			@JsonProperty("toSpawnRange")int toSpawnRange) {
		this.blueprintName = blueprintName;
		this.toSpawn = toSpawn;
		this.toSpawnRange = toSpawnRange;
	}

	public String getBlueprintName() {
		return blueprintName;
	}

	public int getToSpawn() {
		return toSpawn;
	}

	public int getToSpawnRange() {
		return toSpawnRange;
	}
}
