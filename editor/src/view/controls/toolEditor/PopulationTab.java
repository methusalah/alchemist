package view.controls.toolEditor;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.util.Callback;
import model.ES.serial.Blueprint;
import model.ES.serial.BlueprintLibrary;
import model.world.PopulationTool;
import model.world.Tool;

public class PopulationTab extends Tab implements ToolEditor {
	private final ListProperty<Blueprint> blueprintList;
	private final PopulationTool tool;

	public PopulationTab(PopulationTool tool) {
		this.tool = tool;
		setText("Entities");
		setClosable(false);
		ListView<Blueprint> list = new ListView<>();
		
		setContent(list);
		
		blueprintList = new SimpleListProperty<Blueprint>(FXCollections.observableArrayList(BlueprintLibrary.getAllBlueprints()));
		list.setItems(blueprintList);
		
		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Blueprint>() {

			@Override
			public void changed(ObservableValue<? extends Blueprint> observable, Blueprint oldValue, Blueprint newValue) {
				if(newValue != null){
					tool.setBlueprint(newValue);
				}
			}
		});
		
		list.setCellFactory(new Callback<ListView<Blueprint>, ListCell<Blueprint>>() {

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
	
	@Override
	public Tool getTool() {
		return tool;
	}

}
