package view.controls.propertyEditor;

import java.beans.PropertyDescriptor;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import util.LogUtil;
import util.geometry.geom2d.Point2D;

import com.simsilica.es.EntityComponent;

public class Point2DEditor extends PropertyEditor{
	private static final int WIDTH = 60;

	TextField xField, yField;
	
	public Point2DEditor(EntityComponent comp, PropertyDescriptor pd) {
		super(comp, pd);
	}

	@Override
	protected void createEditor() {
		HBox box = new HBox(5);
		setCenter(box);
		// y label
		box.getChildren().add(new Label("X"));

		// x text field
		xField = new TextField();
		xField.setPrefWidth(WIDTH);
		xField.addEventHandler(ActionEvent.ACTION, actionHandler);
		xField.setOnMouseEntered(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				editionMode = true;
//				LogUtil.info("taggle");
			}
			
		});

		box.getChildren().add(xField);
		
		// y label
		box.getChildren().add(new Label("Y"));
		
		// y texte field
		yField = new TextField();
		yField.setPrefWidth(WIDTH);
		yField.addEventHandler(ActionEvent.ACTION, actionHandler);
		box.getChildren().add(yField);
	}

	@Override
	protected Object getPropertyValue() {
		return new Point2D(Double.parseDouble(xField.getText()),Double.parseDouble(yField.getText()));  
	}

	@Override
	protected void setPropertyValue(Object o) {
		Point2D p = (Point2D)o;
		xField.setText(df.format(p.x));
		yField.setText(df.format(p.y));
	}
	
	

}
