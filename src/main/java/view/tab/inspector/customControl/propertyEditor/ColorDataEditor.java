package view.tab.inspector.customControl.propertyEditor;

import java.beans.PropertyDescriptor;

import com.simsilica.es.EntityComponent;

import javafx.event.ActionEvent;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import model.tempImport.ColorData;
import view.util.Consumer3;

public class ColorDataEditor extends PropertyEditor{
	
	ColorPicker picker;
	
	public ColorDataEditor(EntityComponent comp, PropertyDescriptor pd, Consumer3<EntityComponent, String, Object> updateCompFunction) {
		super(comp, pd, updateCompFunction);
	}

	@Override
	protected void createEditor() {
		picker = new ColorPicker();
		picker.setPrefWidth(100);
		picker.addEventHandler(ActionEvent.ACTION, e -> applyChange(e));
		picker.focusedProperty().addListener(e -> setEditionMode());		
		setCenter(picker);
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
