package view.controls;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.util.Duration;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;

public class Pot extends Label {

	boolean dragging = false;
	double rate = 0;
	
	EventHandler<MouseEvent> actionHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			LogUtil.info("mouseeventttttt" + event);
			updateRate();
		}
	};
	Timeline timer = new Timeline(new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {

	    @Override
	    public void handle(ActionEvent event) {
	    	updateRate();
	    }
	}));

	
	public Pot() {
		getScene().addEventHandler(MouseEvent.MOUSE_MOVED, actionHandler);
		timer.setCycleCount(Timeline.INDEFINITE);
		setText("bonjour");
		setOnDragDetected(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent event) {
		    	LogUtil.info("drag start");
		    	dragging = true;
		    	Dragboard db = startDragAndDrop(TransferMode.ANY);
//		    	ClipboardContent content = new ClipboardContent();
//	            // Store node ID in order to know what is dragged.
//	            content.putString("ah !");
	            timer.playFromStart();
	            db.setContent(null);
	            event.consume();
		    }
		});
		setOnDragDone(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		    	LogUtil.info("drag end");
		    	dragging = false;
		    	timer.stop();
		        event.consume();
		    }
		});
	}
	
	private void updateRate(){
		LogUtil.info("tagada");
//		Point2D vec = new Point2D(event.getX(), event.getY());
//		rate = vec.getAngle()/AngleUtil.FLAT + 0.5;
//		setRotate(AngleUtil.toDegrees(vec.getAngle()));
	}
	
	
	
}
