package view.controls.toolEditor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import util.event.EventManager;
import util.event.scene.MapSavedEvent;
import util.event.scene.ToolChangedEvent;

public class WorldEditor extends VBox{
	
	public WorldEditor(TitledPane container) {
		container.expandedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue)
					EventManager.post(new ToolChangedEvent(null));
			}
		});
		
		getChildren().add(getSaveButton());
		
		TabPane tabpane = new TabPane();
		tabpane.getTabs().add(new HeighmapTab());
		tabpane.getTabs().add(new AtlasTab());
		tabpane.getTabs().add(new EntityTab());
		tabpane.getTabs().add(new TrinketTab());
		getChildren().add(tabpane);
		
	}
	
	private Button getSaveButton(){
		Button res = new Button("Save the map");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				EventManager.post(new MapSavedEvent());
			}
		});
		return res;
	}


}
