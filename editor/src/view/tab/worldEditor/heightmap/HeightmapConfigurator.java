package view.tab.worldEditor.heightmap;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import view.tab.worldEditor.Toolconfigurator;
import view.tab.worldEditor.pencil.PencilConfigurator;
import view.tab.worldEditor.presenter.HeightMapToolPresenter;
import view.tab.worldEditor.presenter.PencilToolPresenter;
import view.tab.worldEditor.presenter.Tool;
import view.tab.worldEditor.presenter.HeightMapToolPresenter.Operation;
import view.tab.worldEditor.presenter.PencilToolPresenter.Shape;
import view.util.ViewLoader;

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
