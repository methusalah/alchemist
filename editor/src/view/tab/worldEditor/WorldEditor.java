package view.tab.worldEditor;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import presentation.worldEditor.WorldEditorPresenter;
import presentation.worldEditor.WorldTool;
import view.ViewPlatform;
import view.common.WorldEditorInputListener;
import view.tab.worldEditor.atlas.AtlasConfigurator;
import view.tab.worldEditor.heightmap.HeightmapConfigurator;
import view.tab.worldEditor.population.PopulationConfigurator;
import view.util.ViewLoader;

public class WorldEditor extends BorderPane{
	private final Tab container;
	private final WorldEditorPresenter presenter;
	private final WorldEditorInputListener inputListener;

	@FXML
	private TabPane toolTabPane;
	
	@FXML
	private Tab reliefTab, textureTab, entityTab;
	
	public WorldEditor(Tab container) {
		this.container = container;
		presenter = new WorldEditorPresenter();
		inputListener = new WorldEditorInputListener(presenter);
		
		ViewLoader.loadFXMLForControl(this);
	}
	
	@FXML
	private void initialize(){
		reliefTab.setContent(new HeightmapConfigurator());
		textureTab.setContent(new AtlasConfigurator());
		entityTab.setContent(new PopulationConfigurator());
		
		// we listen for the editor tab selection, to disable/enable the scene input listening when user leave/come back.
		container.selectedProperty().addListener((obs, oldValue, newValue) -> {
			Toolconfigurator editor = (Toolconfigurator)(toolTabPane.selectionModelProperty().getValue().getSelectedItem().getContent());
			if(newValue)
				presenter.selectTool((WorldTool)(editor.getTool()));
			setSceneInputListening(newValue);
		});
		
		// we listen for the tool selection to select the tool to trigger.
		// we have to enable the scene listener each time a tool is selected because user may
		// go elsewhere and desactivate it, without deselecting the editor tab
		toolTabPane.selectionModelProperty().getValue().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			Toolconfigurator editor = (Toolconfigurator)newValue.getContent();
			presenter.selectTool((WorldTool)(editor.getTool()));
			setSceneInputListening(true);
		});
	}
	
	private void setSceneInputListening(boolean value){
		if(value && !ViewPlatform.getSceneInputManager().hasListener(inputListener))
			ViewPlatform.getSceneInputManager().addListener(inputListener);
		else if(!value && ViewPlatform.getSceneInputManager().hasListener(inputListener))
			ViewPlatform.getSceneInputManager().removeListener(inputListener);
	}
}
