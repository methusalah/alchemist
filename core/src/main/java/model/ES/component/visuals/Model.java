package model.ES.component.visuals;

import com.simsilica.es.EntityComponent;

public class Model implements EntityComponent {
	private final String path;
	private final double scale;
	private final double yawFix;
	private final double pitchFix;
	private final double rollFix;
	private boolean created = false;
	
	public Model(String path) {
		this(path, 1);
	}
	public Model(String path, double scale) {
		this(path, scale, 0, 0, 0);
	}
	public Model(String path, double scale, double yawFix, double pitchFix, double rollFix) {
		this.path = path;
		this.scale = scale;
		this.yawFix = yawFix;
		this.pitchFix = pitchFix;
		this.rollFix = rollFix;
	}

	public String getPath() {
		return path;
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

	public double getScale() {
		return scale;
	}

	public boolean isCreated() {
		return created;
	}

	public void setCreated() {
		this.created = true;
	}
}
