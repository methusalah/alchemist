package view;

import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.ES.serial.Blueprint;
import presenter.EntityNode;
import presenter.ResourcePresenter;

public class ResourceTab extends Tab {
	private final ResourcePresenter presenter;
	ListView<Blueprint> list;

	public ResourceTab() {
		presenter = new ResourcePresenter();
		
		setText("Ressources");
		setClosable(false);
		VBox content = new VBox();
		setContent(content);
		content.setMaxHeight(Double.MAX_VALUE);
		content.setPadding(new Insets(3));

		list = new ListView<Blueprint>();
		list.itemsProperty().bind(presenter.getBlueprintListProperty());
		list.setMaxHeight(Double.MAX_VALUE);
		configureCellFactoryForDragAndDrop(list);

		content.getChildren().add(list);
	}

	private void configureCellFactoryForDragAndDrop(ListView<Blueprint> list) {
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

				cell.setOnDragDetected(e -> {
					Dragpool.setContent(cell.getItem());
	                Dragboard db = cell.startDragAndDrop(TransferMode.ANY);
                	ClipboardContent content = new ClipboardContent();
                	content.putString("");
                	db.setContent(content);
	                e.consume();
				});
				return cell;
			}
		});
		
		list.setOnDragOver(e -> {
			if (Dragpool.containsType(EntityNode.class))
				e.acceptTransferModes(TransferMode.ANY);
			e.consume();
		});

		list.setOnDragDropped(e -> {
			if (Dragpool.containsType(EntityNode.class))
				presenter.saveEntity((EntityNode) Dragpool.grabContent(EntityNode.class));
		});
	}

}
