package ECS.component.motion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

import util.LogUtil;
import util.math.Angle;


public class PlanarNeededRotation implements EntityComponent {
	public final Angle angle;
	
	public PlanarNeededRotation() {
		angle = new Angle(0);
	}
	public PlanarNeededRotation(@JsonProperty("angle")Angle angle) {
		if(angle.getValue() == 0)
			LogUtil.warning("You should not ask for a null rotation.");
		this.angle = angle;
	}

	public Angle getAngle() {
		return angle;
	}
}
