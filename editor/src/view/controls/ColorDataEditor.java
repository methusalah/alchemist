package view.controls;

import java.beans.PropertyDescriptor;

import model.ES.richData.ColorData;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import com.simsilica.es.EntityComponent;

public class ColorDataEditor extends PropertyEditor{
	
	ColorPicker picker;
	
	public ColorDataEditor(EntityComponent comp, PropertyDescriptor pd) {
		super(comp, pd);
	}

	@Override
	protected void createEditorIn(Pane p) {
		HBox box = new HBox(5);
		box.setAlignment(Pos.CENTER_LEFT);
		p.getChildren().add(box);
		
		picker = new ColorPicker();
		picker.addEventHandler(ActionEvent.ACTION, actionHandler);
		box.getChildren().add(picker);
	}

	@Override
	protected Object getPropertyValue() {
		Color c = picker.getValue();
		ColorData res = new ColorData(c.getOpacity(), c.getRed(), c.getGreen(), c.getBlue());
		return res;
	}

	@Override
	protected void setPropertyValue(Object o) {
		ColorData cd = (ColorData)o;
		picker.setValue(new Color((double)cd.r/255,
				(double)cd.g/255,
				(double)cd.b/255,
				(double)cd.a/255));
	}
	
	

}
