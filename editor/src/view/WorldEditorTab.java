package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import presenter.WorldEditorPresenter;
import view.controls.toolEditor.AtlasTab;
import view.controls.toolEditor.HeighmapTab;
import view.controls.toolEditor.PopulationTab;
import view.controls.toolEditor.ToolEditor;
import view.controls.toolEditor.TrinketTab;

public class WorldEditorTab extends Tab {
	
	WorldEditorPresenter presenter;
	
	public WorldEditorTab(WorldEditorPresenter presenter) {
		setText("World editor");
		setClosable(false);
		
		VBox content = new VBox();
		content.getChildren().add(getSaveButton());
		
		TabPane tabpane = new TabPane();
		tabpane.getTabs().add(new HeighmapTab(presenter.getHeightmapTool()));
		tabpane.getTabs().add(new AtlasTab(presenter.getAtlasTool()));
		tabpane.getTabs().add(new PopulationTab(presenter.getPopulationTool()));
		tabpane.getTabs().add(new TrinketTab());
		content.getChildren().add(tabpane);

		tabpane.selectionModelProperty().getValue().selectedItemProperty().addListener(new ChangeListener<Tab>() {

			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
				ToolEditor editor = (ToolEditor)newValue;
				presenter.selectTool(editor.getTool());
			}
		});

		setOnSelectionChanged(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				if(getTabPane().selectionModelProperty().get().getSelectedItem() != WorldEditorTab.this)
					presenter.selectTool(null);
				else
					presenter.selectTool(((ToolEditor)tabpane.selectionModelProperty().getValue().getSelectedItem()).getTool());
			}
		});
		setContent(content);
	}
	
	private Button getSaveButton(){
		Button res = new Button("Save the map");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				presenter.saveWorld();
			}
		});
		return res;
	}


}
