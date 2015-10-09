package view.controls;

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
	protected void createEditorIn(Pane p) {
		p.setMaxWidth(Double.MAX_VALUE);
		HBox box = new HBox(5);
		box.setMaxWidth(Double.MAX_VALUE);
		box.setAlignment(Pos.CENTER_LEFT);
		p.getChildren().add(box);
		
		valueField = new TextField();
		valueField.setMaxWidth(Double.MAX_VALUE);
		valueField.addEventHandler(ActionEvent.ACTION, actionHandler);
		box.getChildren().add(valueField);
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
