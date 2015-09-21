package model.ES.component.shipGear;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class Gun implements EntityComponent{
	public final EntityId holder;
	
	public Gun(EntityId holder) {
		this.holder = holder;
	}
	
}
