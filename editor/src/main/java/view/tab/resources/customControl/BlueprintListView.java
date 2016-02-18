package main.java.view.tab.resources.customControl;

import java.util.function.Consumer;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import main.java.model.tempImport.Blueprint;
import main.java.presentation.EntityNode;
import main.java.view.util.Dragpool;

public class BlueprintListView extends ListView<Blueprint>{
	
	Consumer<EntityNode> saveEntityFunction = null;
	
	public BlueprintListView() {
		setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
		setMaxSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
		setCellFactory(callback -> {
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
			cell.setOnDragDetected(e -> {
				Dragpool.setContent(cell.getItem());
                Dragboard db = cell.startDragAndDrop(TransferMode.ANY);
            	ClipboardContent content = new ClipboardContent();
            	content.putString("");
            	db.setContent(content);
                e.consume();
			});
			return cell;
		});
		
		setOnDragOver(e -> {
			if (Dragpool.containsType(EntityNode.class))
				e.acceptTransferModes(TransferMode.ANY);
			e.consume();
		});

		setOnDragDropped(e -> {
			if (Dragpool.containsType(EntityNode.class) && saveEntityFunction != null){
				saveEntityFunction.accept((EntityNode) Dragpool.grabContent(EntityNode.class));
			}
		});
	}
	
	public void setOnSaveEntity(Consumer<EntityNode> saveEntityFunction){
		this.saveEntityFunction = saveEntityFunction;
	}
	
}