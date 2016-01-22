package view.controls.custom;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.world.terrain.TerrainTexture;

public class TerrainTextureButton extends ToggleButton {
	private final TerrainTexture texture;
	
	public TerrainTextureButton(TerrainTexture texture) {
		this.texture = texture;
		Image icon = null;
		try {
			icon = new Image(new FileInputStream("assets/"+texture.getDiffuse()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(icon == null)
			setText("Error : can't find texture "+texture.getDiffuse());
		else {
			ImageView iv = new ImageView(icon);
			Pane p = new Pane(iv);
			p.heightProperty().addListener((obs, oldValue, newValue) -> iv.setFitHeight(newValue.doubleValue()));
			p.widthProperty().addListener((obs, oldValue, newValue) -> iv.setFitWidth(newValue.doubleValue()));
			setGraphic(p);
		}
		setMinSize(80, 80);
	}
}
