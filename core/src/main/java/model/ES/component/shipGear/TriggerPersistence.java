package model.ES.component.shipGear;

import com.simsilica.es.EntityComponent;

public class TriggerPersistence implements EntityComponent {
	public final int duration;
	public final int range;
	
	public TriggerPersistence(int duration, int range) {
		this.duration = duration;
		this.range = range;
	}
}
