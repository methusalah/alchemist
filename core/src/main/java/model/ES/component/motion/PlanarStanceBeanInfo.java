package model.ES.component.motion;

import model.ES.component.ComponentBeamInfo;

public class PlanarStanceBeanInfo extends ComponentBeamInfo {
	
	public PlanarStanceBeanInfo() {
		addPropertyDescriptor(PlanarStance.class, "coord", "", "Actual coordinate of the entity.");
		addPropertyDescriptor(PlanarStance.class, "orientation", "", "Actual planar orientation of the entity.");
		addPropertyDescriptor(PlanarStance.class, "elevation", "", "Z coordinate of the entity. 0 is default.");
		addPropertyDescriptor(PlanarStance.class, "upVector", "Up vector", "Vector indicating entity's top to tilt the entity.");
		
	}
}
