package view.controls.toolEditor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.world.atlas.AtlasTool.OPERATION;
import view.controls.custom.IconButton;
import view.controls.toolEditor.parameter.AtlasToolPresenter;

public class AtlasTab extends Tab {
	
	AtlasToolPresenter toolPresenter = new AtlasToolPresenter();

	public AtlasTab() {
		setText("Texture Atlas");
		setClosable(false);
		VBox content = new VBox();
		content.getChildren().add(getOperationPane());
		content.getChildren().add(new PencilEditor(toolPresenter));
		setContent(content);
	}
	
	private Node getOperationPane(){
		return new BorderPane(getPropagateSmoothButton(), null, getAddDeleteButton(), null, null);
	}
	
	private Button getAddDeleteButton(){
		IconButton res = new IconButton("assets/textures/editor/rise_low_icon.png", "Rise/Low");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				toolPresenter.setOperation(OPERATION.Add_Delete);
			}
		});
		return res;
	}

	private Button getPropagateSmoothButton(){
		IconButton res = new IconButton("assets/textures/editor/noise_smooth_icon.png", "Noise/Smooth");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				toolPresenter.setOperation(OPERATION.Propagate_Smooth);
			}
		});
		return res;
	}
}
