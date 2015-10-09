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

public class IntegerEditor extends PropertyEditor{
	
	TextField valueField;
	
	public IntegerEditor(EntityComponent comp, PropertyDescriptor pd) {
		super(comp, pd);
	}

	@Override
	protected void createEditorIn(Pane p) {
		HBox box = new HBox(5);
		box.setAlignment(Pos.CENTER_LEFT);
		p.getChildren().add(box);
		
		valueField = new TextField();
		valueField.addEventHandler(ActionEvent.ACTION, actionHandler);
		box.getChildren().add(valueField);
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
