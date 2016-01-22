package view.worldEdition;

import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.world.terrain.TerrainTexture;
import presenter.worldEdition.Tool;
import presenter.worldEdition.atlas.AtlasToolPresenter;
import presenter.worldEdition.atlas.AtlasToolPresenter.Operation;
import view.controls.custom.IconToggleButton;
import view.controls.custom.TerrainTextureButton;

public class AtlasTab extends Tab implements ToolEditor {
	private final AtlasToolPresenter presenter;
	private final VBox textureGrid; 

	public AtlasTab() {
		presenter = new AtlasToolPresenter(this);
		setText("Texture Atlas");
		setClosable(false);
		textureGrid = new VBox();
		VBox content = new VBox();
		content.getChildren().add(getOperationPane());
		content.getChildren().add(getTexturePane());
		content.getChildren().add(new PencilEditor(presenter));
		setContent(content);
		updateTextureGrid();
		presenter.getTextures().addListener((ListChangeListener.Change<? extends TerrainTexture> e) -> updateTextureGrid());
	}
	
	private Node getOperationPane(){
		VBox res = new VBox();
		res.getChildren().add(new Label("Operation"));
		res.getChildren().add(new BorderPane(getPropagateSmoothButton(), null, getAddDeleteButton(), null, null));
		return res;
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
	
	private Node getTexturePane(){
		VBox res = new VBox();
		res.getChildren().add(new Label("Texture"));
		res.getChildren().add(textureGrid);
		return res;
	}
	
	public void updateTextureGrid(){
		textureGrid.getChildren().clear();
		HBox row1 = new HBox(getTerrainTextureButton(0),
				getTerrainTextureButton(1),
				getTerrainTextureButton(2),
				getTerrainTextureButton(3));
		HBox row2 = new HBox(getTerrainTextureButton(4),
				getTerrainTextureButton(5),
				getTerrainTextureButton(6),
				getTerrainTextureButton(7));
		textureGrid.getChildren().addAll(row1, row2);
	}
	
	private Node getTerrainTextureButton(int textureIndex){
		Pane res = new Pane();
		res.setMinSize(80, 100);
		res.setMaxSize(80, 100);
		if(presenter.getTextures().size() > textureIndex){
			TerrainTextureButton texButton = new TerrainTextureButton(presenter.getTextures().get(textureIndex));
			texButton.prefWidthProperty().bind(res.widthProperty());
			texButton.prefHeightProperty().bind(texButton.widthProperty());
			Button edit = new Button("Edit");
			edit.prefWidthProperty().bind(res.widthProperty());
			res.getChildren().add(new VBox(texButton, edit));		
		} else {
			Button set = new Button("Set texture");
			set.prefWidthProperty().bind(res.widthProperty());
			set.prefHeightProperty().bind(res.heightProperty());
			res.getChildren().add(set);
		}
		return res;
	}
	

}
