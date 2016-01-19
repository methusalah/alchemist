package view;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import presenter.EditorPlatform;
import presenter.WorldEditorPresenter;
import presenter.common.WorldEditorInputListener;
import view.controls.toolEditor.AtlasTab;
import view.controls.toolEditor.HeighmapTab;
import view.controls.toolEditor.PopulationTab;
import view.controls.toolEditor.ToolEditor;
import view.controls.toolEditor.TrinketTab;

public class WorldEditorTab extends Tab {
	private final WorldEditorPresenter presenter;
	private final WorldEditorInputListener inputListener;
	
	TabPane tabpane;
	
	public WorldEditorTab() {
		presenter = new WorldEditorPresenter(this);
		inputListener = new WorldEditorInputListener(presenter);
		
		setText("World editor");
		setClosable(false);
		
		VBox content = new VBox();
		Button saveButton = new Button("Save the map");
		saveButton.setOnAction(e -> presenter.saveWorld());
		content.getChildren().add(saveButton);
		
		tabpane = new TabPane(
				new HeighmapTab(presenter.getHeightmapTool()),
				new AtlasTab(presenter.getAtlasTool()),
				new PopulationTab(presenter.getPopulationTool()),
				new TrinketTab());
		
		tabpane.selectionModelProperty().getValue().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			ToolEditor editor = (ToolEditor)newValue;
			presenter.selectTool(editor.getTool());
			setSceneInputListening(true);
		});
		
		selectedProperty().addListener(e -> {
			ToolEditor editor = ((ToolEditor)tabpane.selectionModelProperty().getValue().getSelectedItem());
			if(isSelected())
				presenter.selectTool(editor.getTool());
			setSceneInputListening(isSelected());
		});					

		content.getChildren().add(tabpane);
		setContent(content);
	}
	
	private void setSceneInputListening(boolean value){
		if(value && !EditorPlatform.getSceneInputManager().hasListener(inputListener))
			EditorPlatform.getSceneInputManager().addListener(inputListener);
		else
			EditorPlatform.getSceneInputManager().removeListener(inputListener);
	}
}
