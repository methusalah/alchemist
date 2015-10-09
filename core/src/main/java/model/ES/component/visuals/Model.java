package model.ES.component.visuals;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class Model implements EntityComponent {
	public final String path;
	public final double scale;
	public final double yawFix;
	public final double pitchFix;
	public final double rollFix;
	public boolean created = false;
	
	public Model(String path) {
		this(path, 1);
	}
	public Model(String path, double scale) {
		this(path, scale, 0, 0, 0);
	}
	public Model(@JsonProperty("path")String path,
			@JsonProperty("scale")double scale,
			@JsonProperty("yawFix")double yawFix,
			@JsonProperty("pitchFix")double pitchFix,
			@JsonProperty("rollFix")double rollFix) {
		this.path = path;
		this.scale = scale;
		this.yawFix = yawFix;
		this.pitchFix = pitchFix;
		this.rollFix = rollFix;
	}
	public boolean isCreated() {
		return created;
	}
	public void setCreated(boolean created) {
		this.created = created;
	}
	public String getPath() {
		return path;
	}
	public double getScale() {
		return scale;
	}
	public double getYawFix() {
		return yawFix;
	}
	public double getPitchFix() {
		return pitchFix;
	}
	public double getRollFix() {
		return rollFix;
	}

}
