package view.typeEditor;

import java.lang.reflect.Field;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import util.event.ComponentFieldChange;
import util.event.EventManager;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;

import com.simsilica.es.EntityComponent;

public class Point3DEditorCtrl {
	private EntityComponent comp;
	private Field field;
	
	@FXML
	private TextField xValue;

	@FXML
	private TextField yValue;

	@FXML
	private TextField zValue;

	@FXML
	private void initialize() {
		
	}
	
	public void setField(EntityComponent comp, Field field){
		this.comp = comp;
		this.field = field;
		try {
			xValue.setText(Double.toString(((Point3D)field.get(comp)).x));
			yValue.setText(Double.toString(((Point3D)field.get(comp)).y));
			zValue.setText(Double.toString(((Point3D)field.get(comp)).z));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public void changeValue(){
		double x = Double.parseDouble(xValue.getText());
		double y = Double.parseDouble(yValue.getText());
		double z = Double.parseDouble(zValue.getText());
		EventManager.post(new ComponentFieldChange(comp, field.getName(), new Point3D(x, y, z)));
	}
}
