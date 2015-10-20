package view;

import com.jme3x.jfx.injfx.JmeForImageView;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class SceneView extends Pane {

	public SceneView(JmeForImageView jme) {
		setStyle("-fx-background-color: darkgrey");
		
		ImageView image = new ImageView();
		image.fitHeightProperty().bind(heightProperty());
		image.fitWidthProperty().bind(widthProperty());
		image.setStyle("-fx-background-color: blue");
		getChildren().add(image);

		jme.bind(image);
	}
}
