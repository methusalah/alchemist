package view.controls.toolEditor;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.ES.serial.Blueprint;
import model.ES.serial.BlueprintLibrary;
import model.world.EntityInstancierTool;
import model.world.HeightMapTool.OPERATION;
import model.world.PencilTool.MODE;
import model.world.PencilTool.SHAPE;
import util.event.EventManager;
import util.event.scene.ToolChangedEvent;
import view.controls.toolEditor.parameter.HeightMapParameter;
import view.controls.toolEditor.parameter.PopulationParameter;

public class PopulationEditor extends ListView<Blueprint>{

	private final ListProperty<Blueprint> blueprintList;

	public PopulationEditor() {
		blueprintList = new SimpleListProperty<Blueprint>(FXCollections.observableArrayList(BlueprintLibrary.getAllBlueprints()));
		setItems(blueprintList);
		
		getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Blueprint>() {

			@Override
			public void changed(ObservableValue<? extends Blueprint> observable, Blueprint oldValue, Blueprint newValue) {
				if(newValue != null){
					EventManager.post(new ToolChangedEvent(new PopulationParameter(newValue)));
				}
			}
		});
		
		setCellFactory(new Callback<ListView<Blueprint>, ListCell<Blueprint>>() {

			@Override
			public ListCell<Blueprint> call(ListView<Blueprint> list) {
				ListCell<Blueprint> cell = new ListCell<Blueprint>() {

					@Override
					protected void updateItem(Blueprint item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							setText(item.getName());
						} else {
							setText(null);
							setGraphic(null);
						}
					}
				};
				return cell;
			}
		});
	}
}
