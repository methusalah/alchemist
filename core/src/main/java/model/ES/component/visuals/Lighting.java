package model.ES.component.visuals;

import java.awt.Color;

import com.simsilica.es.EntityComponent;

public class Lighting implements EntityComponent {
	public final Color color;
	public final double intensity;
	public final double distance;
	public final double innerAngle;
	public final double outerAngle;
	public final boolean shadowCaster;
	public final double activationRate;
	
	public Lighting(Color color, double intensity, double distance, double innerAngle, double outerAngle, boolean shadowCaster, double activationRate) {
		this.color = color;
		this.intensity = intensity;
		this.distance = distance;
		this.innerAngle = innerAngle;
		this.outerAngle = outerAngle;
		this.shadowCaster = shadowCaster;
		this.activationRate = activationRate;
	}
}
