package view.controls.propertyEditor;

import java.beans.PropertyDescriptor;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import com.simsilica.es.EntityComponent;

public class BooleanEditor extends PropertyEditor{
	
	CheckBox valueBox;
	
	public BooleanEditor(EntityComponent comp, PropertyDescriptor pd) {
		super(comp, pd);
	}

	@Override
	protected void createEditor() {
		valueBox = new CheckBox();
		valueBox.addEventHandler(ActionEvent.ACTION, actionHandler);
		valueBox.focusedProperty().addListener(focusChangeHandler);
		setCenter(valueBox);
	}

	@Override
	protected Object getPropertyValue() {
		return valueBox.isSelected(); 
	}

	@Override
	protected void setPropertyValue(Object o) {
		boolean v = (Boolean)o;
		valueBox.setSelected(v);
	}
	
	

}
