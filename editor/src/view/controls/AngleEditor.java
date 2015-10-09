package view.controls;

import java.beans.PropertyDescriptor;

import model.ES.richData.Angle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;

import com.simsilica.es.EntityComponent;

public class AngleEditor extends PropertyEditor{
	
	EventHandler<ActionEvent> valueCorrectionHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			correctValue();
		}
	};

	TextField valueField;
	
	public AngleEditor(EntityComponent comp, PropertyDescriptor pd) {
		super(comp, pd);
		valueField.addEventHandler(ActionEvent.ACTION, valueCorrectionHandler);
	}

	@Override
	protected void createEditorIn(Pane p) {
		p.setMaxWidth(Double.MAX_VALUE);
		HBox box = new HBox(5);
		box.setMaxWidth(Double.MAX_VALUE);
		box.setAlignment(Pos.CENTER_LEFT);
		p.getChildren().add(box);
		
		valueField = new TextField();
		valueField.addEventHandler(ActionEvent.ACTION, actionHandler);
		box.getChildren().add(valueField);
	}

	@Override
	protected Object getPropertyValue() {
		return new Angle(AngleUtil.toRadians(Double.parseDouble(valueField.getText())));  
	}

	@Override
	protected void setPropertyValue(Object o) {
		Angle a = (Angle)o;
		valueField.setText(Double.toString(AngleUtil.toDegrees(a.getValue())));
	}
	
	private void correctValue() {
		double a = (Double)getPropertyValue();
		setPropertyValue(AngleUtil.normalize(a));
	}
	

}
