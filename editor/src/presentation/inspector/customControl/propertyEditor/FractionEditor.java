package presentation.inspector.customControl.propertyEditor;

import java.beans.PropertyDescriptor;

import presenter.InspectorPresenter;

import com.simsilica.es.EntityComponent;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import presentation.commonControl.Knob;
import util.math.Angle;
import util.math.AngleUtil;
import util.math.Fraction;

public class FractionEditor extends PropertyEditor{
	TextField valueField;
	Slider slider;
	
	public FractionEditor(InspectorPresenter presenter, EntityComponent comp, PropertyDescriptor pd) {
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
		
		slider = new Slider();
		slider.setMax(1);
		slider.setMin(0);
		slider.focusedProperty().addListener(e -> setEditionMode());
		slider.valueProperty().addListener(e -> applyChange(new ActionEvent(slider, null)));
		box.getChildren().add(slider);
	}

	@Override
	protected Object getPropertyValue() {
		return new Fraction(Double.parseDouble(valueField.getText()));
	}

	@Override
	protected void setPropertyValue(Object o) {
		Fraction a = (Fraction)o;
		valueField.setText(df.format(a.getValue()));
		slider.setValue(a.getValue());
	}
	
	@Override
	protected void applyChange(ActionEvent event) {
		if(event.getSource() == slider)
			valueField.setText(df.format(slider.getValue()));
		else{
			slider.setValue(Double.parseDouble(valueField.getText()));
		}
		super.applyChange(event);
	}
}
