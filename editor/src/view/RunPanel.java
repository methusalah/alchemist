package view;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import presenter.RunPresenter;

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
		
		Slider tickDurationSlider = new Slider(1, 50, 20);
		tickDurationSlider.valueProperty().bindBidirectional(presenter.getMillisPerTickProperty());
		getChildren().add(tickDurationSlider);
	}
}
