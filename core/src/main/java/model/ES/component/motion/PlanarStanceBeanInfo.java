package model.ES.component.motion;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class PlanarStanceBeanInfo extends  SimpleBeanInfo {
	private final static Class myClass = PlanarStance.class;
    public PropertyDescriptor[] getPropertyDescriptors()
    {
        try {
            PropertyDescriptor flc = new PropertyDescriptor("coord", myClass, "getCoord", null);
            flc.setDisplayName("Coordinate");
            flc.setShortDescription("tagada tsouin tsouin");
            PropertyDescriptor fic = new PropertyDescriptor("orientation", myClass, "getOrientation", null);
            PropertyDescriptor pct = new PropertyDescriptor("elevation", myClass, "getElevation", null);
            PropertyDescriptor[] list = { flc, fic, pct };
            return list;
        }
        catch (IntrospectionException iexErr)
        {
            throw new Error(iexErr.toString());
        }
    }
}
