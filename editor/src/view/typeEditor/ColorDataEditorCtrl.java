package view.typeEditor;

import java.lang.reflect.Field;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import model.ES.richData.ColorData;
import util.event.ComponentPropertyChanged;
import util.event.EventManager;

import com.simsilica.es.EntityComponent;

public class ColorDataEditorCtrl {
	private EntityComponent comp;
	private Field field;
	
	@FXML
	private ColorPicker picker;

	@FXML
	private void initialize() {
		
	}
	
	public void setField(EntityComponent comp, Field field){
		this.comp = comp;
		this.field = field;
		try {
			ColorData c = (ColorData)field.get(comp);
			picker.setValue(new Color((double)c.r/255, (double)c.g/255, (double)c.b/255, (double)c.a/255));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public void changeValue(){
		Color c = picker.getValue();
		EventManager.post(new ComponentPropertyChanged(comp, field.getName(), new ColorData((int)Math.round(c.getOpacity()*255),
				(int)Math.round(c.getRed()*255),
				(int)Math.round(c.getGreen()*255),
				(int)Math.round(c.getBlue()*255))));
	}
}
