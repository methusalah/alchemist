package model.ES.component.motion.physic;

import com.simsilica.es.EntityComponent;

public class Dragging implements EntityComponent {
	private final double dragging;
	
	public Dragging(double dragging) {
		this.dragging = dragging;
	}

	public double getDragging() {
		return dragging;
	}
}
