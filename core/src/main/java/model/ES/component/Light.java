package model.ES.component;

import java.awt.Color;

import com.simsilica.es.EntityComponent;

public class Light implements EntityComponent {
	public final Color color;
	public final double intensity;
	public final double distance;
	public final double innerAngle;
	public final double outerAngle;
	public final boolean shadowCaster;
	
	public Light(Color color, double intensity, double distance, double innerAngle, double outerAngle, boolean shadowCaster) {
		this.color = color;
		this.intensity = intensity;
		this.distance = distance;
		this.innerAngle = innerAngle;
		this.outerAngle = outerAngle;
		this.shadowCaster = shadowCaster;
	}
}
