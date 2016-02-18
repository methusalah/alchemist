package main.java.view.tab.worldEditor.atlas;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.java.presentation.worldEditor.Tool;
import main.java.presentation.worldEditor.atlas.AtlasToolPresenter;
import main.java.presentation.worldEditor.atlas.AtlasViewable;
import main.java.presentation.worldEditor.atlas.AtlasToolPresenter.Operation;
import main.java.view.control.TerrainTextureButton;
import main.java.view.tab.worldEditor.Toolconfigurator;
import main.java.view.tab.worldEditor.pencil.PencilConfigurator;
import main.java.view.util.ViewLoader;

public class AtlasConfigurator extends VBox implements Toolconfigurator, AtlasViewable {
	private final AtlasToolPresenter presenter;
	ToggleGroup textureGroup;

	@FXML
	private ToggleButton addDeleteButton, propagateSmoothButton;

	@FXML
	private VBox textureGrid;
	
	public AtlasConfigurator() {
		presenter = new AtlasToolPresenter(this);
		ViewLoader.loadFXMLForControl(this);
	}
	
	@FXML
	private void initialize(){
		addDeleteButton.selectedProperty().bindBidirectional(presenter.getOperationProperty().getToggle(Operation.ADD_DELETE));
		propagateSmoothButton.selectedProperty().bindBidirectional(presenter.getOperationProperty().getToggle(Operation.PROPAGATE_SMOOTH));
		getChildren().add(new PencilConfigurator(presenter));
		updateTextureGrid();
	}

	@Override
	public Tool getTool() {
		return presenter;
	}
	
	@Override
	public void updateTextureGrid(){
		textureGrid.getChildren().clear();
		textureGrid.setPrefSize(400, 200);
		if(presenter.getTextures().getValue() == null){
			BorderPane p = new BorderPane(new Label("Click on a region to show it's textures."));
			p.setMinSize(400, 200);
			
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
	
	@Override
	public Optional<TerrainTexture> showTerrainTextureDialog(TerrainTexture texture){
		TerrainTextureDialog dialog = new TerrainTextureDialog(texture);
		return dialog.showAndWait();
	}
}
