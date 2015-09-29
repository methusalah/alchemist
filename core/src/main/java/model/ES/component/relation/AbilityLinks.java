package model.ES.component.relation;

import java.util.Map;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class AbilityLinks implements EntityComponent{
	public final Map<String, EntityId> entities;
	
	public AbilityLinks(Map<String, EntityId> entites) {
		this.entities = entites;
	}
}
