package view.worldEdition;

import javafx.scene.control.Tab;
import presenter.worldEdition.Tool;

public class TrinketTab extends Tab implements ToolEditor {

	public TrinketTab() {
		setText("Trinkets");
		setClosable(false);
	}
	
	@Override
	public Tool getTool() {
		return null;
	}

}
