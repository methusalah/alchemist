package presentation.resources;

import java.util.function.Consumer;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;
import model.ES.serial.Blueprint;
import presenter.common.EntityNode;
import util.LogUtil;
import view.Dragpool;

public class BlueprintListView extends ListView<Blueprint>{
	
	Consumer<EntityNode> saveEntityFunction = null;
	
	public BlueprintListView() {
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
