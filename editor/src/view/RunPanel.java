package view;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import presenter.RunPresenter;
import util.event.EventManager;
import util.event.scene.RunEvent;

public class RunPanel extends HBox {
	private final RunPresenter presenter = new RunPresenter();

	public RunPanel() {
		setStyle("-fx-background-color: darkgrey");
		Button runButton = new Button("Run");
		runButton.setOnAction(e -> presenter.run());
		getChildren().add(runButton);

		Button stopButton = new Button("Stop");
		stopButton.setOnAction(e -> presenter.stop());
		getChildren().add(stopButton);
	}
}
