package view;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3x.jfx.injfx.JmeForImageView;
import com.sun.corba.se.impl.oa.poa.AOMEntry;

import controller.DraggableCamera;
import controller.ECS.EntitySystem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import util.LogUtil;
import util.event.EntityCreationEvent;
import util.event.EventManager;
import util.event.scene.RunEvent;
import util.geometry.geom2d.Point2D;

public class RunPanel extends HBox {
	

	public RunPanel() {
		setStyle("-fx-background-color: darkgrey");
		Button runButton = new Button("Run");
		runButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				EventManager.post(new RunEvent(true));
			}
		});
		getChildren().add(runButton);

		Button stopButton = new Button("Stop");
		stopButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				EventManager.post(new RunEvent(false));
			}
		});
		getChildren().add(stopButton);
	}
}
