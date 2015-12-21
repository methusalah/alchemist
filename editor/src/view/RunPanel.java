package view;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import util.event.EventManager;
import util.event.scene.RunEvent;

public class RunPanel extends HBox {
	

	public RunPanel() {
		setStyle("-fx-background-color: darkgrey");
		Button runButton = new Button("Run");
		runButton.setOnAction(e -> EventManager.post(new RunEvent(true)));
		getChildren().add(runButton);

		Button stopButton = new Button("Stop");
		stopButton.setOnAction(e -> EventManager.post(new RunEvent(false)));
		getChildren().add(stopButton);
	}
}
