package plugin.infiniteWorld.editor.view.heightmap;

import com.brainless.alchemist.view.util.ViewLoader;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import plugin.infiniteWorld.editor.presentation.HeightmapConfiguratorPresenter;
import plugin.infiniteWorld.editor.presentation.Tool;
import plugin.infiniteWorld.editor.presentation.HeightmapConfiguratorPresenter.Operation;
import plugin.infiniteWorld.editor.view.Toolconfigurator;
import plugin.infiniteWorld.editor.view.pencil.PencilConfigurator;

public class HeightmapConfigurator extends VBox implements Toolconfigurator {

	private final HeightmapConfiguratorPresenter presenter;

	@FXML
	private ToggleButton raiseLowButton, noiseSmoothButton, uniformResetButton;

	
	public HeightmapConfigurator() {
		presenter = new HeightmapConfiguratorPresenter();
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
