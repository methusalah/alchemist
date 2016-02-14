package presentation.worldEditor.heightmap;

import javafx.scene.layout.VBox;
import presentation.util.ViewLoader;
import presentation.worldEditor.pencil.PencilConfigurator;

public class HeightmapConfigurator extends VBox {
	
	public HeightmapConfigurator() {
		ViewLoader.loadFXMLForControl(this);
		getChildren().add(new PencilConfigurator());
	}

}
