package presentation.worldEditor;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import presentation.util.ViewLoader;

public class WorldEditor extends BorderPane{
	
	@FXML
	private Tab reliefTab, textureTab, entityTab;
	
	public WorldEditor() {
		ViewLoader.loadFXMLForControl(this);
	}

}
