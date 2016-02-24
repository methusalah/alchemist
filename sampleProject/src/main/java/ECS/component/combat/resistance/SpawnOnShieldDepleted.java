package ECS.component.combat.resistance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class SpawnOnShieldDepleted implements EntityComponent {
	private final List<String> blueprintNames;
	private final int lastShieldValue;
	
	public SpawnOnShieldDepleted() {
		blueprintNames = Collections.unmodifiableList(new ArrayList<>());
		lastShieldValue = 0;
	}
	
	public SpawnOnShieldDepleted(@JsonProperty("blueprintNames")List<String> blueprintNames,
			@JsonProperty("lastShieldValue")int lastShieldValue) {
		this.blueprintNames = Collections.unmodifiableList(new ArrayList<>(blueprintNames));
		this.lastShieldValue = lastShieldValue; 
	}

	public List<String> getBlueprintNames() {
		return blueprintNames;
	}

	public int getLastShieldValue() {
		return lastShieldValue;
	}
	
}
