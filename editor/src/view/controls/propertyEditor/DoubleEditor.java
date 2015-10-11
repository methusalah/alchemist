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

public class DoubleEditor extends PropertyEditor{
	
	TextField valueField;
	
	public DoubleEditor(EntityComponent comp, PropertyDescriptor pd) {
		super(comp, pd);
	}

	@Override
	protected void createEditor() {
		valueField = new TextField();
		valueField.setMaxWidth(100);
		valueField.addEventHandler(ActionEvent.ACTION, actionHandler);
		setCenter(valueField);
	}

	@Override
	protected Object getPropertyValue() {
		return Double.parseDouble(valueField.getText());  
	}

	@Override
	protected void setPropertyValue(Object o) {
		double v = (Double)o;
		valueField.setText(df.format(v));
	}
	
	

}
