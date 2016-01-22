package view.worldEdition;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import model.world.terrain.TerrainTexture;

public class TerrainTextureDialog extends Dialog<TerrainTexture> {
	private final TerrainTexture terrainTexture;
	
	public TerrainTextureDialog(TerrainTexture terrainTexture) {
		this.terrainTexture = terrainTexture;
		
		setTitle("Terrain texture edition");
		setHeaderText("Set you terrain texture");
		
		getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		
		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
	}
}
