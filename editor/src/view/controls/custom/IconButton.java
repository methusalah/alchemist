package view.controls.custom;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IconButton extends Button{

	public IconButton(String iconpath, String defaultText) {
		Image icon = null;
		try {
			icon = new Image(new FileInputStream(iconpath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(icon == null)
			setText(defaultText);
		else
			setGraphic(new ImageView(icon));
	}
}
