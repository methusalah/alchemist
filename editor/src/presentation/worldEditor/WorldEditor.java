package presentation.worldEditor;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import model.EditorPlatform;
import presentation.common.WorldEditorInputListener;
import presentation.util.ViewLoader;
import presentation.worldEditor.atlas.AtlasConfigurator;
import presentation.worldEditor.heightmap.HeightmapConfigurator;
import presentation.worldEditor.presenter.WorldEditorPresenter;
import presentation.worldEditor.presenter.WorldTool;
import view.worldEdition.Toolconfigurator;

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
		
		container.selectedProperty().addListener((obs, oldValue, newValue) -> {
			Toolconfigurator editor = (Toolconfigurator)(toolTabPane.selectionModelProperty().getValue().getSelectedItem());
			if(newValue)
				presenter.selectTool((WorldTool)(editor.getTool()));
			setSceneInputListening(newValue);
		});					
	}
	
	private void setSceneInputListening(boolean value){
		if(value && !EditorPlatform.getSceneInputManager().hasListener(inputListener))
			EditorPlatform.getSceneInputManager().addListener(inputListener);
		else if(!value && EditorPlatform.getSceneInputManager().hasListener(inputListener))
			EditorPlatform.getSceneInputManager().removeListener(inputListener);
	}
}
