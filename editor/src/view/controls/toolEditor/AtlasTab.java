package view.controls.toolEditor;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.world.Tool;
import model.world.atlas.AtlasTool;
import view.controls.custom.IconToggleButton;

public class AtlasTab extends Tab implements ToolEditor {
	private final AtlasTool tool;
	private final ToggleGroup group = new ToggleGroup();

	public AtlasTab(AtlasTool tool) {
		this.tool = tool;
		setText("Texture Atlas");
		setClosable(false);
		VBox content = new VBox();
		content.getChildren().add(getOperationPane());
		content.getChildren().add(new PencilEditor(tool));
		setContent(content);
	}
	
	private Node getOperationPane(){
		return new BorderPane(getPropagateSmoothButton(), null, getAddDeleteButton(), null, null);
	}
	
	private ToggleButton getAddDeleteButton(){
		IconToggleButton res = new IconToggleButton("assets/textures/editor/rise_low_icon.png", "Rise/Low");
		res.selectedProperty().bindBidirectional(tool.getAddDeleteProperty());
		res.setToggleGroup(group);
		return res;
	}

	private ToggleButton getPropagateSmoothButton(){
		IconToggleButton res = new IconToggleButton("assets/textures/editor/noise_smooth_icon.png", "Noise/Smooth");
		res.selectedProperty().bindBidirectional(tool.getPropagateSmoothProperty());
		res.setToggleGroup(group);
		return res;
	}
	
	@Override
	public Tool getTool() {
		return tool;
	}

}
