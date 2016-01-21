package view.worldEdition;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import presenter.worldEdition.Tool;
import presenter.worldEdition.atlas.AtlasToolPresenter;
import presenter.worldEdition.atlas.AtlasToolPresenter.Operation;
import view.controls.custom.IconToggleButton;

public class AtlasTab extends Tab implements ToolEditor {
	private final AtlasToolPresenter presenter;

	public AtlasTab(AtlasToolPresenter tool) {
		this.presenter = tool;
		setText("Texture Atlas");
		setClosable(false);
		VBox content = new VBox();
		content.getChildren().add(getOperationPane());
		content.getChildren().add(new PencilEditor(tool));
		content.getChildren().add(getTextureList());
		setContent(content);
		
		
	}
	
	private Node getOperationPane(){
		return new BorderPane(getPropagateSmoothButton(), null, getAddDeleteButton(), null, null);
	}
	
	private ToggleButton getAddDeleteButton(){
		IconToggleButton res = new IconToggleButton("assets/textures/editor/rise_low_icon.png", "Rise/Low");
		res.selectedProperty().bindBidirectional(presenter.getOperationProperty().getToggle(Operation.ADD_DELETE));
		res.setSelected(true);
		return res;
	}

	private ToggleButton getPropagateSmoothButton(){
		IconToggleButton res = new IconToggleButton("assets/textures/editor/noise_smooth_icon.png", "Propagate/Smooth");
		res.selectedProperty().bindBidirectional(presenter.getOperationProperty().getToggle(Operation.PROPAGATE_SMOOTH));
		return res;
	}
	
	@Override
	public Tool getTool() {
		return presenter;
	}
	
	private ListView<String> getTextureList(){
		ListView<String> res;
		res = new ListView<>(presenter.getTextures());
		res.setEditable(true);
		res.setCellFactory(TextFieldListCell.forListView());		
		res.setOnEditCommit(e -> res.getItems().set(e.getIndex(), e.getNewValue()));
		res.setOnEditCancel(e -> System.out.println("setOnEditCancel"));
		return res;
	}

}
