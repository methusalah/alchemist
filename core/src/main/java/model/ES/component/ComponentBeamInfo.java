package model.ES.component;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public abstract class ComponentBeamInfo extends  SimpleBeanInfo {
	List<PropertyDescriptor> descriptors = new ArrayList<>();
    public PropertyDescriptor[] getPropertyDescriptors(){
    	PropertyDescriptor[] res = new PropertyDescriptor[descriptors.size()];
    	for(int i = 0; i < descriptors.size(); i++)
    		res[i] = descriptors.get(i);
    	return res;
    }
    
    protected void addPropertyDescriptor(Class<?> c, String name, String displayName, String info){
        try {
        	String getter;
        	if(c.getField(name).getType() == boolean.class)
        		getter = "is";
        	else
        		getter = "get";
        	getter = getter+name.substring(0, 1).toUpperCase()+name.substring(1);
        		
			PropertyDescriptor p = new PropertyDescriptor(name, c, getter, null);
			if(displayName.isEmpty())
				displayName = name.substring(0, 1).toUpperCase()+name.substring(1);
			p.setDisplayName(displayName);
			p.setShortDescription(info);
			descriptors.add(p);
		} catch (IntrospectionException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
    	
    }
    
    
}