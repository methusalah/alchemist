package plugin.infiniteWorld.editor.view.population;

import com.brainless.alchemist.model.ECS.blueprint.Blueprint;
import com.brainless.alchemist.view.util.ViewLoader;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import plugin.infiniteWorld.editor.presentation.PopulationConfiguratorPresenter;
import plugin.infiniteWorld.editor.presentation.Tool;
import plugin.infiniteWorld.editor.view.Toolconfigurator;

public class PopulationConfigurator extends VBox implements Toolconfigurator {
	private final PopulationConfiguratorPresenter presenter;
	private final ListView<Blueprint> list = new ListView<>();
	
	public PopulationConfigurator() {
		ViewLoader.loadFXMLForControl(this);
		presenter = new PopulationConfiguratorPresenter(this);
	}
	
	@FXML
	private void initialize(){
		getChildren().add(list);
		list.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue != null)
				presenter.setBlueprint(newValue);
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
	
	public void majBlueprintList(ObservableList<Blueprint> blueprintList){
		list.setItems(blueprintList);
	}
	
	@Override
	public Tool getTool() {
		return presenter;
	}

}
