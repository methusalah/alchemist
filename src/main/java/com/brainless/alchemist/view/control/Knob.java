package com.brainless.alchemist.view.control;



import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;

public class Knob extends Pane {

	boolean dragging = false;
	double orientation;
	
	public Knob(double orientation) {
		setPrefSize(25, 25);
		Circle c1 = new Circle(12.5, 12.5, 12.5, Color.LIGHTGRAY);
		c1.setStroke(Color.GRAY);

		Circle c2 = new Circle(12.5+8, 12.5, 2, Color.LIGHTBLUE);
		c2.setStroke(Color.GRAY);
		
		Circle c3 = new Circle(12.5, 12.5, 0.5, Color.LIGHTGRAY);
		c3.setStroke(Color.gray(0.6));

		getChildren().add(c1);
		getChildren().add(c2);
		getChildren().add(c3);
		setOnMouseDragged(e -> {
	    	updateRate(e);
	        e.consume();
		});
		setOrientation(orientation);
	}
	
	private void updateRate(MouseEvent event){
		Point2D mouse = new Point2D(event.getSceneX(), event.getSceneY());
		javafx.geometry.Point2D p = this.localToScene(new javafx.geometry.Point2D(5, 5));
		Point2D controlCenter = new Point2D(p.getX(), p.getY());
		
		Point2D vec = mouse.getSubtraction(controlCenter);
		setOrientation(AngleUtil.toDegrees(-vec.getAngle()));
		this.fireEvent(new ActionEvent(this, null));
	}
	
	public void setOrientation(double value){
		setRotate(-value);
		orientation = value;
	}
	public double getOrientation(){
		return orientation;
	}
	
	
	
}