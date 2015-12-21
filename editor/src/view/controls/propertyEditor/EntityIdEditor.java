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
import com.simsilica.es.EntityId;

public class EntityIdEditor extends PropertyEditor{
	
	TextField valueField;
	
	public EntityIdEditor(EntityComponent comp, PropertyDescriptor pd) {
		super(comp, pd);
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
		return new EntityId(Long.parseLong(valueField.getText()));  
	}

	@Override
	protected void setPropertyValue(Object o) {
		EntityId v = (EntityId)o;
		if(v == null)
			valueField.setText(null);
		else
			valueField.setText(Long.toString(v.getId()));
	}
	
	

}
