package view.controls.custom;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.world.terrain.TerrainTexture;

public class TerrainTextureButton extends ToggleButton {
	public TerrainTextureButton(TerrainTexture texture) {
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
			iv.fitHeightProperty().bind(p.heightProperty());
			iv.fitWidthProperty().bind(p.widthProperty());
			iv.setPreserveRatio(true);
			setGraphic(p);
		}
	}
}
