package presentation.worldEditor.pencil;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;
import presentation.util.ViewLoader;
import presentation.worldEditor.presenter.PencilToolPresenter;
import presentation.worldEditor.presenter.PencilToolPresenter.Mode;
import presentation.worldEditor.presenter.PencilToolPresenter.Shape;

public class PencilConfigurator extends VBox {
	
	private final PencilToolPresenter presenter;
	
	@FXML
	private ToggleButton circleButton, squareButton, diamondButton, airbrushButton, roughButton, noiseButton;
	
	@FXML
	private Slider sizeSlider, strengthSlider;
	
	@FXML
	private TextField sizeField, strengthField;
	
	public PencilConfigurator(PencilToolPresenter presenter) {
		this.presenter = presenter;
		ViewLoader.loadFXMLForControl(this);
	}
	
	@FXML
	private void initialize(){
		circleButton.selectedProperty().bindBidirectional(presenter.getShapeProperty().getToggle(Shape.CIRCLE));
		squareButton.selectedProperty().bindBidirectional(presenter.getShapeProperty().getToggle(Shape.SQUARE));
		diamondButton.selectedProperty().bindBidirectional(presenter.getShapeProperty().getToggle(Shape.DIAMOND));
		
		airbrushButton.selectedProperty().bindBidirectional(presenter.getModeProperty().getToggle(Mode.AIRBRUSH));
		roughButton.selectedProperty().bindBidirectional(presenter.getModeProperty().getToggle(Mode.ROUGH));
		noiseButton.selectedProperty().bindBidirectional(presenter.getModeProperty().getToggle(Mode.NOISE));
		
		sizeSlider.setMax(PencilToolPresenter.MAX_SIZE);
		sizeSlider.valueProperty().bindBidirectional(presenter.getSizeProperty());
		sizeField.textProperty().bindBidirectional(presenter.getSizeProperty(), new NumberStringConverter());
		
		strengthSlider.valueProperty().bindBidirectional(presenter.getStrengthProperty());
		strengthField.textProperty().bindBidirectional(presenter.getStrengthProperty(), new NumberStringConverter());
	}

}
