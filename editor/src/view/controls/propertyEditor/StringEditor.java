package view.controls.propertyEditor;

import java.beans.PropertyDescriptor;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import util.geometry.geom2d.Point2D;

import com.simsilica.es.EntityComponent;

public class StringEditor extends PropertyEditor{
	
	TextField valueField;
	
	public StringEditor(EntityComponent comp, PropertyDescriptor pd) {
		super(comp, pd);
	}

	@Override
	protected void createEditor() {
		HBox box = new HBox(5);
		box.setMaxWidth(Double.MAX_VALUE);
		box.setAlignment(Pos.CENTER_LEFT);
		
		valueField = new TextField();
		valueField.setMaxWidth(Double.MAX_VALUE);
		valueField.addEventHandler(ActionEvent.ACTION, actionHandler);
		valueField.focusedProperty().addListener(focusChangeHandler);
		setCenter(valueField);
	}

	@Override
	protected Object getPropertyValue() {
		return valueField.getText();  
	}

	@Override
	protected void setPropertyValue(Object o) {
		String v = (String)o;
		valueField.setText(v);
	}
	
	

}
