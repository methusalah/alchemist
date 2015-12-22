package view.controls.toolEditor;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.world.HeightMapTool;
import model.world.HeightMapTool.OPERATION;
import model.world.Tool;
import view.controls.custom.IconButton;

public class HeighmapTab extends Tab implements ToolEditor {
	
	private final HeightMapTool tool;

	public HeighmapTab(HeightMapTool tool) {
		
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
	
	private Button getRiseLowButton(){
		IconButton res = new IconButton("assets/textures/editor/rise_low_icon.png", "Rise/Low");
		res.setOnAction(e -> tool.setOperation(OPERATION.Raise_Low));
		return res;
	}

	private Button getNoiseSmoothButton(){
		IconButton res = new IconButton("assets/textures/editor/noise_smooth_icon.png", "Noise/Smooth");
		res.setOnAction(e -> tool.setOperation(OPERATION.Noise_Smooth));
		return res;
	}

	private Button getUniformResetButton(){
		IconButton res = new IconButton("assets/textures/editor/uniform_reset_icon.png", "Uniform/Reset");
		res.setOnAction(e -> tool.setOperation(OPERATION.Uniform_Reset));
		return res;
	}

	@Override
	public Tool getTool() {
		return tool;
	}
}
