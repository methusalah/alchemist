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

public class IntegerEditor extends PropertyEditor{
	
	TextField valueField;
	
	public IntegerEditor(EntityComponent comp, PropertyDescriptor pd) {
		super(comp, pd);
	}

	@Override
	protected void createEditor() {
		valueField = new TextField();
		valueField.setPrefWidth(100);
		valueField.addEventHandler(ActionEvent.ACTION, actionHandler);
		valueField.focusedProperty().addListener(focusChangeHandler);
		setCenter(valueField);
	}

	@Override
	protected Object getPropertyValue() {
		return Integer.parseInt(valueField.getText());  
	}

	@Override
	protected void setPropertyValue(Object o) {
		int v = (Integer)o;
		valueField.setText(Integer.toString(v));
	}
	
	

}
