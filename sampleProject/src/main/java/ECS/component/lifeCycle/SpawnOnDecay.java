package ECS.component.lifeCycle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.simsilica.es.EntityComponent;

public class SpawnOnDecay implements EntityComponent {
	private final List<String> blueprintNames;
	
	public SpawnOnDecay() {
		blueprintNames = Collections.unmodifiableList(new ArrayList<>());
	}
	
	public SpawnOnDecay(List<String> blueprintNames) {
		this.blueprintNames = Collections.unmodifiableList(new ArrayList<>(blueprintNames));
	}

	public List<String> getBlueprintNames() {
		return blueprintNames;
	}
}
