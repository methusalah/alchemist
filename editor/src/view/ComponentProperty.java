package view;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.controlsfx.control.PropertySheet;

import com.simsilica.es.EntityComponent;

import util.event.ComponentPropertyChanged;
import util.event.EventManager;

public class ComponentProperty implements PropertySheet.Item{
    private final EntityComponent comp;
    private final PropertyDescriptor beanPropertyDescriptor;
    private final Method readMethod;

    public ComponentProperty(EntityComponent comp, PropertyDescriptor propertyDescriptor) {
        this.comp = comp;
        this.beanPropertyDescriptor = propertyDescriptor;
        readMethod = propertyDescriptor.getReadMethod();
    }
    
    /** {@inheritDoc} */
    @Override public String getName() {
        return beanPropertyDescriptor.getDisplayName();
    }
    
    /** {@inheritDoc} */
    @Override public String getDescription() {
        return beanPropertyDescriptor.getShortDescription();
    }
    
    /** {@inheritDoc} */
    @Override public Class<?> getType() {
        return beanPropertyDescriptor.getPropertyType();
    }

    /** {@inheritDoc} */
    @Override public Object getValue() {
        try {
            return readMethod.invoke(comp);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /** {@inheritDoc} */
    @Override public void setValue(Object value) {
    	EventManager.post(new ComponentPropertyChanged(comp, beanPropertyDescriptor.getName(), value));
//    	LogUtil.info("tsouin tsouin");
    }

    /** {@inheritDoc} */
    @Override public String getCategory() {
        return beanPropertyDescriptor.isExpert()? "Expert": "Basic";
    }
}
