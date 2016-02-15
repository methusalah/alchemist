package presentation.worldEditor.heightmap;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import presentation.util.ViewLoader;
import presentation.worldEditor.pencil.PencilConfigurator;
import presentation.worldEditor.presenter.HeightMapToolPresenter;
import presentation.worldEditor.presenter.PencilToolPresenter;
import presentation.worldEditor.presenter.HeightMapToolPresenter.Operation;
import presentation.worldEditor.presenter.PencilToolPresenter.Shape;

public class HeightmapConfigurator extends VBox {

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


}
