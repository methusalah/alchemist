package component.lifeCycle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class SpawnMultipleOnBorn implements EntityComponent {
	private final String blueprintName;
	private final int count;
	private final int range;
	
	public SpawnMultipleOnBorn(){
		blueprintName = "";
		count = 0;
		range = 0;
	}
	
	public SpawnMultipleOnBorn(@JsonProperty("blueprintName")String blueprintName,
			@JsonProperty("count")int count,
			@JsonProperty("range")int range) {
		this.blueprintName = blueprintName;
		this.count = count;
		this.range = range;
	}

	public String getBlueprintName() {
		return blueprintName;
	}

	public int getCount() {
		return count;
	}

	public int getRange() {
		return range;
	}
}
