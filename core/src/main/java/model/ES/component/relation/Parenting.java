package model.ES.component.relation;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class Parenting implements EntityComponent {
	public final EntityId parent;
	
	public Parenting(EntityId parent) {
		this.parent = parent;
	}
}
