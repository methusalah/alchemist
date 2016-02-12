package view.worldEdition;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
	ToggleGroup textureGroup;

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
		textureGroup = new ToggleGroup();
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
		textureGrid.setPrefSize(400, 200);
		if(presenter.getTextures().getValue() == null){
			BorderPane p = new BorderPane(new Label("Click on a region to show it's textures."));
			p.setPrefSize(400, 200);
			textureGrid.getChildren().add(p);
		} else {
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
	}
	
	private Node getTerrainTextureButton(int textureIndex){
		BorderPane res = new BorderPane();
		res.setPrefSize(100, 100);
		//res.setMinSize(100, 100);
		if(presenter.getTextures().size() > textureIndex && presenter.getTextures().get(textureIndex) != null){
			TerrainTextureButton texButton = new TerrainTextureButton(presenter.getTextures().get(textureIndex));
			texButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			texButton.setToggleGroup(textureGroup);
			texButton.setSelected(presenter.getActualTextureProperty().getValue() == textureIndex);
			texButton.selectedProperty().addListener((obs, oldValue, newValue) -> {
				if(newValue)
					presenter.getActualTextureProperty().set(textureIndex);
			});
			
			Button edit = new Button("Edit");
			edit.setOnMouseClicked(e -> presenter.editTerrainTexture(textureIndex));
			edit.setMaxWidth(Double.MAX_VALUE);
			edit.setPadding(new Insets(3));
			Button delete = new Button("X");
			delete.setOnMouseClicked(e -> presenter.deleteTerrainTexture(textureIndex)); 
			delete.setPadding(new Insets(3));
			BorderPane editionPane = new BorderPane(edit, null, delete, null, null);
			editionPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			
			res.setCenter(texButton);
			res.setBottom(editionPane);
		} else {
			ToggleButton set = new ToggleButton("Click to set texture");
			set.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			set.setToggleGroup(textureGroup);
			set.setSelected(presenter.getActualTextureProperty().getValue() == textureIndex);
			set.setOnMouseClicked(e -> presenter.editTerrainTexture(textureIndex));
			res.setCenter(set);
		}
		return res;
	}
	
	public Optional<TerrainTexture> showTerrainTextureDialog(TerrainTexture texture){
		TerrainTextureDialog dialog = new TerrainTextureDialog(texture);
		return dialog.showAndWait();
	}
}
