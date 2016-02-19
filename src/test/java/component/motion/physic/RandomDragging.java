package component.motion.physic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class RandomDragging implements EntityComponent {
	private final double dragCoef;
	private final double dragCoefRange;
	
	public RandomDragging() {
		dragCoef = 0;
		dragCoefRange = 0;
	}
	
	public RandomDragging(@JsonProperty("dragCoef")double dragCoef,
			@JsonProperty("dragCoefRange")double dragCoefRange) {
		this.dragCoef = dragCoef;
		this.dragCoefRange = dragCoefRange;
	}

	public double getDragCoef() {
		return dragCoef;
	}

	public double getDragCoefRange() {
		return dragCoefRange;
	}
}
