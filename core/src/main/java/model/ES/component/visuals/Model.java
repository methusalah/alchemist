package model.ES.component.visuals;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

import util.math.Angle;

public class Model implements EntityComponent {
	public final String path;
	public final double scale;
	public final Angle yawFix;
	public final Angle pitchFix;
	public final Angle rollFix;
	public boolean created = false;
	
	
	public Model() {
		path = "";
		scale = 1;
		yawFix = new Angle(0);
		pitchFix = new Angle(0);
		rollFix = new Angle(0);
		created = false;
	}
	
	public Model(String path) {
		this(path, 1);
	}
	public Model(String path, double scale) {
		this(path, scale, new Angle(0), new Angle(0), new Angle(0));
	}
	public Model(@JsonProperty("path")String path,
			@JsonProperty("scale")double scale,
			@JsonProperty("yawFix")Angle yawFix,
			@JsonProperty("pitchFix")Angle pitchFix,
			@JsonProperty("rollFix")Angle rollFix) {
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
	public Angle getYawFix() {
		return yawFix;
	}
	public Angle getPitchFix() {
		return pitchFix;
	}
	public Angle getRollFix() {
		return rollFix;
	}

}
