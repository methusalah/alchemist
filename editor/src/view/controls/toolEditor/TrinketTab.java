package view.controls.toolEditor;

import model.world.Tool;
import javafx.scene.control.Tab;

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
