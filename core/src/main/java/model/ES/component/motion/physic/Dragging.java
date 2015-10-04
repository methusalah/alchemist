package model.ES.component.motion.physic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class Dragging implements EntityComponent {
	public final double dragging;
	
	public Dragging(@JsonProperty("dragging")double dragging) {
		this.dragging = dragging;
	}
}
