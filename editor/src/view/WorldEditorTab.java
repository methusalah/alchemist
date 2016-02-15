package view;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import model.EditorPlatform;
import presentation.common.WorldEditorInputListener;
import presentation.worldEditor.presenter.WorldEditorPresenter;
import presentation.worldEditor.presenter.WorldTool;
import view.worldEdition.AtlasTab;
import view.worldEdition.HeighmapTab;
import view.worldEdition.PopulationTab;
import view.worldEdition.Toolconfigurator;
import view.worldEdition.TrinketTab;

public class WorldEditorTab extends Tab {
	private final WorldEditorPresenter presenter;
	private final WorldEditorInputListener inputListener;
	
	TabPane tabpane;
	
	public WorldEditorTab() {
		presenter = new WorldEditorPresenter();
		inputListener = new WorldEditorInputListener(presenter);
		
		setText("World editor");
		setClosable(false);
		
		VBox content = new VBox();
		Button saveButton = new Button("Save the map");
		saveButton.setOnAction(e -> presenter.saveWorld());
		content.getChildren().add(saveButton);
		
		tabpane = new TabPane(
				new HeighmapTab(),
				new AtlasTab(),
				new PopulationTab(),
				new TrinketTab());
		
		tabpane.selectionModelProperty().getValue().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			Toolconfigurator editor = (Toolconfigurator)newValue;
			presenter.selectTool((WorldTool)(editor.getTool()));
			setSceneInputListening(true);
		});
		
		selectedProperty().addListener(e -> {
			Toolconfigurator editor = ((Toolconfigurator)tabpane.selectionModelProperty().getValue().getSelectedItem());
			if(isSelected())
				presenter.selectTool((WorldTool)(editor.getTool()));
			setSceneInputListening(isSelected());
		});					

		content.getChildren().add(tabpane);
		setContent(content);
	}
	
	private void setSceneInputListening(boolean value){
		if(value && !EditorPlatform.getSceneInputManager().hasListener(inputListener))
			EditorPlatform.getSceneInputManager().addListener(inputListener);
		else if(!value && EditorPlatform.getSceneInputManager().hasListener(inputListener))
			EditorPlatform.getSceneInputManager().removeListener(inputListener);
	}
}
