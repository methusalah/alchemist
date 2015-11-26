package view.controls.toolEditor;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.util.Callback;
import model.ES.serial.Blueprint;
import model.ES.serial.BlueprintLibrary;
import util.event.EventManager;
import util.event.scene.ToolChangedEvent;
import view.controls.toolEditor.parameter.PopulationParameter;

public class PopulationEditor extends ListView<Blueprint>{

	private final ListProperty<Blueprint> blueprintList;

	public PopulationEditor(TitledPane container) {
		container.expandedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue || getSelectionModel().isEmpty())
					EventManager.post(new ToolChangedEvent(null));
				else
					EventManager.post(new ToolChangedEvent(new PopulationParameter(getSelectionModel().getSelectedItem())));
			}
		});
		
		
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
