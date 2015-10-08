package view.typeEditor;

import java.lang.reflect.Field;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import util.event.ComponentPropertyChanged;
import util.event.EventManager;
import util.geometry.geom2d.Point2D;

import com.simsilica.es.EntityComponent;

public class Point2DEditorCtrl {
	private EntityComponent comp;
	private Field field;
	
	@FXML
	private TextField xValue;

	@FXML
	private TextField yValue;

	@FXML
	private void initialize() {
		
	}
	
	public void setField(EntityComponent comp, Field field){
		this.comp = comp;
		this.field = field;
		try {
			xValue.setText(Double.toString(((Point2D)field.get(comp)).x));
			yValue.setText(Double.toString(((Point2D)field.get(comp)).y));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public void changeValue(){
		double x = Double.parseDouble(xValue.getText());
		double y = Double.parseDouble(yValue.getText());
		EventManager.post(new ComponentPropertyChanged(comp, field.getName(), new Point2D(x, y)));
	}
}
