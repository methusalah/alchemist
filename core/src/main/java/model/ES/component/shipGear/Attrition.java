package model.ES.component.shipGear;

import com.simsilica.es.EntityComponent;

public class Attrition implements EntityComponent {
	public final int maxHitpoints, actualHitpoints;
	
	public Attrition(int maxHitpoints, int actualHitpoints) {
		this.maxHitpoints = maxHitpoints;
		this.actualHitpoints = actualHitpoints;
	}
}
