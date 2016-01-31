package model.ES.component.interaction;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

import model.ES.richData.Damage;

public class Damaging implements EntityComponent{
	public final EntityId source, target;
	
	public Damaging() {
		source = null;
		target = null;
	}
	
	public Damaging(EntityId source, EntityId target) {
		this.source = source;
		this.target = target;
	}

	public EntityId getSource() {
		return source;
	}

	public EntityId getTarget() {
		return target;
	}
}
