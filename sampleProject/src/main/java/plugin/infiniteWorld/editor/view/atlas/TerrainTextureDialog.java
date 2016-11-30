package plugin.infiniteWorld.editor.view.atlas;

import java.io.File;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import plugin.infiniteWorld.world.terrain.TerrainTexture;

class TerrainTextureDialog extends Dialog<TerrainTexture> {
	public TerrainTextureDialog(){
		this(new TerrainTexture("", "", 0));
	}
	
	public TerrainTextureDialog(TerrainTexture terrainTexture) {
		setTitle("Terrain texture edition");
		setHeaderText("Set you terrain texture");
		
		ButtonType loginButtonType = new ButtonType("Delete", ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.OK, ButtonType.CANCEL);
		
		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		
		TextField diffusePath = new TextField(terrainTexture == null? "" : terrainTexture.getDiffuse());
		diffusePath.setPromptText("choose a diffuse map");
		Button diffusePicker = new Button("Browse...");
		diffusePicker.setOnMouseClicked(e -> {
			diffusePath.setText(getRelativizedPickedPath());
		});
		
		TextField normalPath = new TextField(terrainTexture == null? "" : terrainTexture.getNormal());
		normalPath.setPromptText("choose a normal map");
		Button normalPicker = new Button("Browse...");
		normalPicker.setOnMouseClicked(e -> {
			normalPath.setText(getRelativizedPickedPath());
		});
		
		TextField scale = new TextField(terrainTexture == null? "" : String.valueOf(terrainTexture.getScale()));
		normalPath.setPromptText("set map scale");
		

		grid.add(new Label("Diffuse :"), 0, 0);
		grid.add(diffusePath, 1, 0);
		grid.add(diffusePicker, 2, 0);
		
		grid.add(new Label("Normal :"), 0, 1);
		grid.add(normalPath, 1, 1);
		grid.add(normalPicker, 2, 1);

		grid.add(new Label("Scale :"), 0, 2);
		grid.add(scale, 1, 2);
		getDialogPane().setContent(grid);
		
		setResultConverter(dialogButton -> {
		    if (dialogButton == ButtonType.OK)
		        return new TerrainTexture(diffusePath.getText(), normalPath.getText(), Double.parseDouble(scale.getText()));
		    else 
		    	return null;
		});
	}
	
	private String getRelativizedPickedPath(){
		File base = new File(System.getProperty("user.dir") + "/assets");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.setInitialDirectory(base);
		File picked = fileChooser.showOpenDialog(getOwner());
		if(picked == null)
			return "";
		else
			return base.toURI().relativize(picked.toURI()).getPath();

	}
	
}
