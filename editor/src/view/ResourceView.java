package view;

import java.util.ArrayList;
import java.util.List;

import model.Blueprint;
import model.EntityPresenter;
import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import util.event.EntityCreationFromBlueprintEvent;
import util.event.EntitySelectionChanged;
import util.event.EventManager;
import util.event.ParentingChangedEvent;
import util.event.SaveEntityEvent;
import view.controls.EntityNodeItem;

import com.simsilica.es.EntityId;

public class ResourceView extends VBox {

	ListView<Blueprint> list;

	public ResourceView() {

		setMaxHeight(Double.MAX_VALUE);
		setPadding(new Insets(3));

		Label title = new Label("Resources");
		title.setMinHeight(40);
		title.setMaxWidth(Double.MAX_VALUE);
		title.setStyle("-fx-background-color: lightblue");
		getChildren().add(title);

		list = new ListView<Blueprint>();
		list.setMaxHeight(Double.MAX_VALUE);
		configureCellFactoryForDragAndDrop(list);

		getChildren().add(list);
	}

	public void setBlueprintList(ListProperty<Blueprint> blueprintList) {
		list.itemsProperty().bind(blueprintList);
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

				cell.setOnDragDetected(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent mouseEvent) {
						Dragpool.setContent(cell.getItem());
						
	                	Dragboard db = cell.startDragAndDrop(TransferMode.ANY);
	                    ClipboardContent content = new ClipboardContent();
	                    content.putString("");
	                    db.setContent(content);

						mouseEvent.consume();
					}
				});
				return cell;
			}
		});
		
		list.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				if (Dragpool.containsType(EntityPresenter.class)) {
					event.acceptTransferModes(TransferMode.ANY);
				}
				event.consume();
			}
		});

		list.setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				if (Dragpool.containsType(EntityPresenter.class)) {
					EventManager.post(new SaveEntityEvent((EntityPresenter) Dragpool.grabContent(EntityPresenter.class)));
				}
			}
		});
	}

}
