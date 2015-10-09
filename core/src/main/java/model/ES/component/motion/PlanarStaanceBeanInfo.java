package model.ES.component.motion;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class PlanarStaanceBeanInfo extends  SimpleBeanInfo {
	private final static Class myClass = PlanarStance.class;
    public PropertyDescriptor[] getPropertyDescriptors()
    {
        try {
            PropertyDescriptor flc = new PropertyDescriptor("coord", myClass, "getCoord", null);
            flc.setDisplayName("Coordinate");
            flc.setShortDescription("Actual coordinate of the entity.");
            PropertyDescriptor fic = new PropertyDescriptor("orientation", myClass, "getOrientation", null);
            PropertyDescriptor pct = new PropertyDescriptor("elevation", myClass, "getElevation", null);
            PropertyDescriptor up = new PropertyDescriptor("upVector", myClass, "getUpVetor", null);
            up.setDisplayName("Up vector");
            flc.setShortDescription("Vector indicating the top vector to tilt the entity.");
            PropertyDescriptor[] list = { flc, fic, pct, up};
            return list;
        }
        catch (IntrospectionException iexErr)
        {
            throw new Error(iexErr.toString());
        }
    }
}
