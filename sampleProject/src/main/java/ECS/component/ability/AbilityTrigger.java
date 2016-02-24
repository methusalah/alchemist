package ECS.component.ability;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class AbilityTrigger implements EntityComponent{
	public final Map<String, Boolean> triggers;
	
	public AbilityTrigger() {
		triggers = new HashMap<>();
	}
	
	public AbilityTrigger(@JsonProperty("triggers")Map<String, Boolean> triggers) {
		this.triggers = triggers;
		triggers.put("bonjour", true);
		triggers.put("aurevoir", false);
		triggers.put("ca va ?", true);
	}

	public Map<String, Boolean> getTriggers() {
		return triggers;
	}
}
