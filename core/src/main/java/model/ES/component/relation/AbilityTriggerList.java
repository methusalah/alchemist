package model.ES.component.relation;

import java.util.Map;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class AbilityTriggerList implements EntityComponent{
	public final Map<String, Boolean> triggers;
	
	public AbilityTriggerList(Map<String, Boolean> triggers) {
		this.triggers = triggers;
	}
}
