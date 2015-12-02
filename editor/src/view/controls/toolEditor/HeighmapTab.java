package view.controls.toolEditor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.world.HeightMapTool.OPERATION;
import view.controls.custom.IconButton;
import view.controls.toolEditor.parameter.HeightmapToolPresenter;

public class HeighmapTab extends Tab {
	
	HeightmapToolPresenter toolPresenter = new HeightmapToolPresenter();

	public HeighmapTab() {
		setText("Heightmap");
		setClosable(false);
		VBox content = new VBox();
		content.getChildren().add(getOperationPane());
		content.getChildren().add(new PencilEditor(toolPresenter));
		setContent(content);
	}
	
	private Node getOperationPane(){
		return new BorderPane(getNoiseSmoothButton(), null, getRiseLowButton(), null, getUniformResetButton());
	}
	
	private Button getRiseLowButton(){
		IconButton res = new IconButton("assets/textures/editor/rise_low_icon.png", "Rise/Low");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				toolPresenter.setOperation(OPERATION.Raise_Low);
			}
		});
		return res;
	}

	private Button getNoiseSmoothButton(){
		IconButton res = new IconButton("assets/textures/editor/noise_smooth_icon.png", "Noise/Smooth");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				toolPresenter.setOperation(OPERATION.Noise_Smooth);
			}
		});
		return res;
	}

	private Button getUniformResetButton(){
		IconButton res = new IconButton("assets/textures/editor/uniform_reset_icon.png", "Uniform/Reset");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				toolPresenter.setOperation(OPERATION.Uniform_Reset);
			}
		});
		return res;
	}


}
