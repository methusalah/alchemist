package model.ES.component.interaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.simsilica.es.EntityComponent;

public class SpawnOnTouch implements EntityComponent {
	private final List<String> blueprintNames;
	
	public SpawnOnTouch() {
		blueprintNames = Collections.unmodifiableList(new ArrayList<>());
	}
	
	public SpawnOnTouch(List<String> blueprintNames) {
		this.blueprintNames = Collections.unmodifiableList(new ArrayList<>(blueprintNames));
	}

	public List<String> getBlueprintNames() {
		return blueprintNames;
	}
	
}
