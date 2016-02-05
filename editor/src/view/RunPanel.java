package view;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.converter.NumberStringConverter;
import presenter.RunPresenter;

public class RunPanel extends HBox {
	private final RunPresenter presenter = new RunPresenter();

	public RunPanel() {
		Button runButton = new Button("Run");
		runButton.setOnAction(e -> presenter.run());
		getChildren().add(runButton);

		Button stopButton = new Button("Stop");
		stopButton.setOnAction(e -> presenter.stop());
		getChildren().add(stopButton);
		
		Slider tickDurationSlider = new Slider(1, 50, 20);
		tickDurationSlider.valueProperty().bindBidirectional(presenter.getMillisPerTickProperty());
		getChildren().add(tickDurationSlider);
		
		TextField tickDurationField = new TextField();
		tickDurationField.textProperty().bindBidirectional(presenter.getMillisPerTickProperty(), new NumberStringConverter());
		getChildren().add(tickDurationField);
	}
}
