package view.worldEdition;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import presenter.worldEdition.HeightMapToolPresenter;
import presenter.worldEdition.Tool;
import presenter.worldEdition.HeightMapToolPresenter.Operation;
import view.controls.custom.IconToggleButton;

public class HeighmapTab extends Tab implements ToolEditor {
	private final HeightMapToolPresenter tool;

	public HeighmapTab(HeightMapToolPresenter tool) {
		
		this.tool = tool;
		setText("Heightmap");
		setClosable(false);
		VBox content = new VBox();
		content.getChildren().add(getOperationPane());
		content.getChildren().add(new PencilEditor(tool));
		setContent(content);
	}
	
	private Node getOperationPane(){
		return new BorderPane(getNoiseSmoothButton(), null, getRiseLowButton(), null, getUniformResetButton());
	}
	
	private ToggleButton getRiseLowButton(){
		IconToggleButton res = new IconToggleButton("assets/textures/editor/rise_low_icon.png", "Rise/Low");
		res.selectedProperty().bindBidirectional(tool.getOperationProperty().getToggle(Operation.RAISE_LOW));
		res.setSelected(true);
		return res;
	}

	private ToggleButton getNoiseSmoothButton(){
		IconToggleButton res = new IconToggleButton("assets/textures/editor/noise_smooth_icon.png", "Noise/Smooth");
		res.selectedProperty().bindBidirectional(tool.getOperationProperty().getToggle(Operation.NOISE_SMOOTH));
		return res;
	}

	private ToggleButton getUniformResetButton(){
		IconToggleButton res = new IconToggleButton("assets/textures/editor/uniform_reset_icon.png", "Uniform/Reset");
		res.selectedProperty().bindBidirectional(tool.getOperationProperty().getToggle(Operation.UNIFORM_RESET));
		return res;
	}

	@Override
	public Tool getTool() {
		return tool;
	}
}
