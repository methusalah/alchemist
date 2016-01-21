package view.worldEdition;

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
import presenter.worldEdition.PopulationTool;
import presenter.worldEdition.Tool;

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
		
		list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue != null)
				tool.setBlueprint(newValue);
		});
		
		list.setCellFactory(e -> {
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
		});
	}
	
	@Override
	public Tool getTool() {
		return tool;
	}

}
