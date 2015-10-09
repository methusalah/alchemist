package view.controls;

import java.beans.PropertyDescriptor;

import util.geometry.geom3d.Point3D;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import com.simsilica.es.EntityComponent;

public class Point3DEditor extends PropertyEditor{
	private static final int WIDTH = 60;
	
	TextField xField, yField, zField;
	
	public Point3DEditor(EntityComponent comp, PropertyDescriptor pd) {
		super(comp, pd);
	}

	@Override
	protected void createEditorIn(Pane p) {
		HBox box = new HBox(5);
		box.setAlignment(Pos.CENTER_LEFT);
		p.getChildren().add(box);
		

		// X label
		box.getChildren().add(new Label("X"));
		
		// x text field
		xField = new TextField();
		xField.setPrefWidth(WIDTH);
		xField.addEventHandler(ActionEvent.ACTION, actionHandler);
		box.getChildren().add(xField);

		// Y label
		box.getChildren().add(new Label("Y"));
		
		// x texte field
		yField = new TextField();
		yField.setPrefWidth(WIDTH);
		yField.addEventHandler(ActionEvent.ACTION, actionHandler);
		box.getChildren().add(yField);

		// z label
		box.getChildren().add(new Label("Z"));
		
		// z texte field
		zField = new TextField();
		zField.setPrefWidth(WIDTH);
		zField.addEventHandler(ActionEvent.ACTION, actionHandler);
		box.getChildren().add(zField);
	}

	@Override
	protected Object getPropertyValue() {
		return new Point3D(Double.parseDouble(xField.getText()),
				Double.parseDouble(yField.getText()),
				Double.parseDouble(zField.getText()));  
	}

	@Override
	protected void setPropertyValue(Object o) {
		Point3D p = (Point3D)o;
		xField.setText(Double.toString(p.x));
		yField.setText(Double.toString(p.y));
		zField.setText(Double.toString(p.z));
	}
	
	

}
