package view.controls.toolEditor;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.world.Tool;
import model.world.atlas.AtlasTool;
import model.world.atlas.AtlasTool.OPERATION;
import view.controls.custom.IconButton;

public class AtlasTab extends Tab implements ToolEditor {
	private final AtlasTool tool;

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
	
	private Button getAddDeleteButton(){
		IconButton res = new IconButton("assets/textures/editor/rise_low_icon.png", "Rise/Low");
		res.setOnAction(e -> tool.setOperation(OPERATION.Add_Delete));
		return res;
	}

	private Button getPropagateSmoothButton(){
		IconButton res = new IconButton("assets/textures/editor/noise_smooth_icon.png", "Noise/Smooth");
		res.setOnAction(e -> tool.setOperation(OPERATION.Propagate_Smooth));
		return res;
	}
	
	@Override
	public Tool getTool() {
		return tool;
	}

}
