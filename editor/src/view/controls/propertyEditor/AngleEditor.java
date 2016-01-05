package view.controls.propertyEditor;

import java.beans.PropertyDescriptor;

import presenter.InspectorPresenter;

import com.simsilica.es.EntityComponent;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import util.math.Angle;
import util.math.AngleUtil;
import view.controls.Knob;

public class AngleEditor extends PropertyEditor{
	TextField valueField;
	Knob knob;
	
	public AngleEditor(InspectorPresenter presenter, EntityComponent comp, PropertyDescriptor pd) {
		super(presenter, comp, pd);
	}

	@Override
	protected void createEditor() {
		HBox box = new HBox(5);
		setCenter(box);
		
		valueField = new TextField();
		valueField.setPrefWidth(100);
		valueField.addEventHandler(ActionEvent.ACTION, e -> applyChange(e));
		valueField.focusedProperty().addListener(e -> setEditionMode());
		box.getChildren().add(valueField);
		
		knob = new Knob(0);
		knob.addEventHandler(ActionEvent.ACTION, e -> applyChange(e));
		knob.focusedProperty().addListener(e -> setEditionMode());
		box.getChildren().add(knob);
	}

	@Override
	protected Object getPropertyValue() {
		return new Angle(AngleUtil.toRadians(knob.getOrientation()));
	}

	@Override
	protected void setPropertyValue(Object o) {
		Angle a = (Angle)o;
		valueField.setText(df.format(AngleUtil.toDegrees(a.getValue())));
		knob.setOrientation(AngleUtil.toDegrees(a.getValue()));
	}
	
	@Override
	protected void applyChange(ActionEvent event) {
		if(event.getSource() == knob)
			valueField.setText(df.format(knob.getOrientation()));
		else{
			// we normalize the angle if the user input is out of range
			double angle = Double.parseDouble(valueField.getText());
			angle = AngleUtil.toDegrees(AngleUtil.normalize(AngleUtil.toRadians(angle)));
			valueField.setText(df.format(angle));
			knob.setOrientation(angle);
		}
		super.applyChange(event);
	}
	
	

}
