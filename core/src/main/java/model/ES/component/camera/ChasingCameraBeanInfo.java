package model.ES.component.camera;

import model.ES.component.ComponentBeamInfo;

public class ChasingCameraBeanInfo extends ComponentBeamInfo{

	public ChasingCameraBeanInfo() {
		addPropertyDescriptor(ChasingCamera.class, "maxSpeed", "Maximum speed", "Maximum speed of this camera in meter/second");
		addPropertyDescriptor(ChasingCamera.class, "speed", "Actual speed", "Actual speed of this camera in meter/second");
		addPropertyDescriptor(ChasingCamera.class, "acceleration", "", "Acceleration in meter/second/second");
		addPropertyDescriptor(ChasingCamera.class, "deceleration", "", "Deceleration in meter/second/second");
    }
}
