package com.brainless.alchemist.view.tab.inspector.customControl.propertyEditor;

import java.beans.PropertyDescriptor;

import com.brainless.alchemist.view.util.Consumer3;
import com.simsilica.es.EntityComponent;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class StringEditor extends PropertyEditor{
	
	TextField valueField;
	
	public StringEditor(EntityComponent comp, PropertyDescriptor pd, Consumer3<EntityComponent, String, Object> updateCompFunction) {
		super(comp, pd, updateCompFunction);
	}

	@Override
	protected void createEditor() {
		HBox box = new HBox(5);
		box.setMaxWidth(Double.MAX_VALUE);
		box.setAlignment(Pos.CENTER_LEFT);
		
		valueField = new TextField();
		valueField.setMaxWidth(Double.MAX_VALUE);
		valueField.addEventHandler(ActionEvent.ACTION, e -> applyChange(e));
		valueField.focusedProperty().addListener(e -> setEditionMode());
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
