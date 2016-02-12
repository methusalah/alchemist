package presentation.actionBar;

import javafx.scene.layout.BorderPane;
import presentation.util.ViewLoader;

public class ActionBar extends BorderPane {
	
	public ActionBar() {
		ViewLoader.loadFXMLForControl(this);
	}

}
