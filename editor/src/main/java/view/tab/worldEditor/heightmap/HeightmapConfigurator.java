package main.java.view.tab.worldEditor.heightmap;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import main.java.presentation.worldEditor.HeightMapToolPresenter;
import main.java.presentation.worldEditor.PencilToolPresenter;
import main.java.presentation.worldEditor.Tool;
import main.java.presentation.worldEditor.HeightMapToolPresenter.Operation;
import main.java.presentation.worldEditor.PencilToolPresenter.Shape;
import main.java.view.tab.worldEditor.Toolconfigurator;
import main.java.view.tab.worldEditor.pencil.PencilConfigurator;
import main.java.view.util.ViewLoader;

public class HeightmapConfigurator extends VBox implements Toolconfigurator {

	private final HeightMapToolPresenter presenter;

	@FXML
	private ToggleButton raiseLowButton, noiseSmoothButton, uniformResetButton;

	
	public HeightmapConfigurator() {
		presenter = new HeightMapToolPresenter();
		ViewLoader.loadFXMLForControl(this);
	}
	
	@FXML
	private void initialize(){
		raiseLowButton.selectedProperty().bindBidirectional(presenter.getOperationProperty().getToggle(Operation.RAISE_LOW));
		noiseSmoothButton.selectedProperty().bindBidirectional(presenter.getOperationProperty().getToggle(Operation.NOISE_SMOOTH));
		uniformResetButton.selectedProperty().bindBidirectional(presenter.getOperationProperty().getToggle(Operation.UNIFORM_RESET));
		getChildren().add(new PencilConfigurator(presenter));
	}

	@Override
	public Tool getTool() {
		return presenter;
	}
}
