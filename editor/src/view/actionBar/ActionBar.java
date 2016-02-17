package view.actionBar;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.NumberStringConverter;
import presentation.actionBar.ActionBarPresenter;
import presentation.actionBar.ActionBarViewer;
import view.util.ViewLoader;

public class ActionBar extends BorderPane implements ActionBarViewer {
	
	private final ActionBarPresenter presenter;
	
	@FXML
	private Slider tickDurationSlider;
	
	@FXML
	private TextField tickDurationField;
	
	@FXML
	private ToggleButton playButton;
	
	public ActionBar() {
		presenter = new ActionBarPresenter(this);
		ViewLoader.loadFXMLForControl(this);
	}

	@FXML
	private void initialize(){
		// view init
		tickDurationSlider.valueProperty().bindBidirectional(presenter.getMillisPerTickProperty());
		tickDurationField.textProperty().bindBidirectional(presenter.getMillisPerTickProperty(), new NumberStringConverter());
		playButton.selectedProperty().addListener((obs, oldValue, newValue) -> presenter.setRun(newValue));
	}
}
