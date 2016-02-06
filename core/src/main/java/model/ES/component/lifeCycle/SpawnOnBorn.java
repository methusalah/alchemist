package model.ES.component.lifeCycle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.simsilica.es.EntityComponent;

public class SpawnOnBorn implements EntityComponent {
	private final List<String> blueprintNames;
	
	public SpawnOnBorn() {
		blueprintNames = Collections.unmodifiableList(new ArrayList<>());
	}
	
	public SpawnOnBorn(List<String> blueprintNames) {
		this.blueprintNames = Collections.unmodifiableList(new ArrayList<>(blueprintNames));
	}

	public List<String> getBlueprintNames() {
		return blueprintNames;
	}
}
