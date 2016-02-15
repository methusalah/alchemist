package view.worldEdition;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import presentation.control.IconToggleButton;
import presentation.worldEditor.presenter.HeightMapToolPresenter;
import presentation.worldEditor.presenter.Tool;
import presentation.worldEditor.presenter.HeightMapToolPresenter.Operation;

public class HeighmapTab extends Tab implements Toolconfigurator {
	private final HeightMapToolPresenter presenter;

	public HeighmapTab() {
		presenter = new HeightMapToolPresenter();
		setText("Heightmap");
		setClosable(false);
		VBox content = new VBox();
		content.getChildren().add(getOperationPane());
		content.getChildren().add(new PencilEditor(presenter));
		setContent(content);
	}
	
	private Node getOperationPane(){
		return new BorderPane(getNoiseSmoothButton(), null, getRiseLowButton(), null, getUniformResetButton());
	}
	
	private ToggleButton getRiseLowButton(){
		IconToggleButton res = new IconToggleButton("assets/textures/editor/rise_low_icon.png", "Rise/Low");
		res.selectedProperty().bindBidirectional(presenter.getOperationProperty().getToggle(Operation.RAISE_LOW));
		res.setSelected(true);
		return res;
	}

	private ToggleButton getNoiseSmoothButton(){
		IconToggleButton res = new IconToggleButton("assets/textures/editor/noise_smooth_icon.png", "Noise/Smooth");
		res.selectedProperty().bindBidirectional(presenter.getOperationProperty().getToggle(Operation.NOISE_SMOOTH));
		return res;
	}

	private ToggleButton getUniformResetButton(){
		IconToggleButton res = new IconToggleButton("assets/textures/editor/uniform_reset_icon.png", "Uniform/Reset");
		res.selectedProperty().bindBidirectional(presenter.getOperationProperty().getToggle(Operation.UNIFORM_RESET));
		return res;
	}

	@Override
	public Tool getTool() {
		return presenter;
	}
}
