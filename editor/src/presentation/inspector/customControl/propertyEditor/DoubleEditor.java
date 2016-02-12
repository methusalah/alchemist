package presentation.inspector.customControl.propertyEditor;

import java.beans.PropertyDescriptor;

import com.simsilica.es.EntityComponent;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import presentation.util.Consumer3;

public class DoubleEditor extends PropertyEditor{
	
	TextField valueField;
	
	public DoubleEditor(EntityComponent comp, PropertyDescriptor pd, Consumer3<EntityComponent, String, Object> updateCompFunction) {
		super(comp, pd, updateCompFunction);
	}

	@Override
	protected void createEditor() {
		valueField = new TextField();
		valueField.setMaxWidth(100);
		valueField.addEventHandler(ActionEvent.ACTION, e -> applyChange(e));
		valueField.focusedProperty().addListener(e -> setEditionMode());
		setCenter(valueField);
	}

	@Override
	protected Object getPropertyValue() {
		return Double.parseDouble(valueField.getText());  
	}

	@Override
	protected void setPropertyValue(Object o) {
		double v = (Double)o;
		valueField.setText(Double.toString(v));
	}
	
	

}
