package model.ES.component.visuals;

import model.ES.richData.ColorData;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class Lighting implements EntityComponent {
	public final ColorData color;
	public final double intensity;
	public final double distance;
	public final double innerAngle;
	public final double outerAngle;
	public final boolean shadowCaster;
	public final double activationRate;
	
	public Lighting(@JsonProperty("color")ColorData color,
			@JsonProperty("intensity")double intensity,
			@JsonProperty("distance")double distance,
			@JsonProperty("innerAngle")double innerAngle,
			@JsonProperty("outerAngle")double outerAngle,
			@JsonProperty("shadowCaster")boolean shadowCaster,
			@JsonProperty("activationRate")double activationRate) {
		this.color = color;
		this.intensity = intensity;
		this.distance = distance;
		this.innerAngle = innerAngle;
		this.outerAngle = outerAngle;
		this.shadowCaster = shadowCaster;
		this.activationRate = activationRate;
	}

	public ColorData getColor() {
		return color;
	}

	public double getIntensity() {
		return intensity;
	}

	public double getDistance() {
		return distance;
	}

	public double getInnerAngle() {
		return innerAngle;
	}

	public double getOuterAngle() {
		return outerAngle;
	}

	public boolean isShadowCaster() {
		return shadowCaster;
	}

	public double getActivationRate() {
		return activationRate;
	}
}
