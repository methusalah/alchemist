package presentation.inspector.customControl.propertyEditor;

import java.beans.PropertyDescriptor;

import presenter.InspectorPresenter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import view.controls.custom.KeyReleasedConsumingTextField;

import com.simsilica.es.EntityComponent;

public class StringEditor extends PropertyEditor{
	
	TextField valueField;
	
	public StringEditor(InspectorPresenter presenter, EntityComponent comp, PropertyDescriptor pd) {
		super(presenter, comp, pd);
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
