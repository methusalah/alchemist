package component.lifeCycle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class SpawnOnBorn implements EntityComponent {
	private final List<String> blueprintNames;
	
	public SpawnOnBorn() {
		blueprintNames = Collections.unmodifiableList(new ArrayList<>());
	}
	
	public SpawnOnBorn(@JsonProperty("blueprintNames")List<String> blueprintNames) {
		this.blueprintNames = Collections.unmodifiableList(new ArrayList<>(blueprintNames));
	}

	public List<String> getBlueprintNames() {
		return blueprintNames;
	}
}
