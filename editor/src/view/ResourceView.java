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
import util.event.EntitySelectionChanged;
import util.event.EventManager;
import util.event.SaveEntityEvent;
import view.controls.EntityNodeItem;

import com.simsilica.es.EntityId;

public class ResourceView extends VBox {
	
	ListView<String> list;
	
	
	public ResourceView() {
		
		setMaxHeight(Double.MAX_VALUE);
		setPadding(new Insets(3));
		
		Label title = new Label("Resources");
		title.setMinHeight(40);
		title.setMaxWidth(Double.MAX_VALUE);
		title.setStyle("-fx-background-color: lightblue");
		getChildren().add(title);
		
		list = new ListView<String>();
		list.setMaxHeight(Double.MAX_VALUE);
		configureCellFactoryForDragAndDrop(list);
		
		getChildren().add(list);
	}
	
	public void setBlueprintList(ListProperty<String> blueprintList){
		list.itemsProperty().bind(blueprintList);
	}
	
	private void configureCellFactoryForDragAndDrop(ListView<String> list){
		list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {

			@Override
	        public ListCell<String> call(ListView<String> list) {
				ListCell<String> cell = new ListCell<String>() {
					
	            	@Override
	                protected void updateItem(String item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (item != null) {
	                    	setText(item);
	                    } else {
	                        setText(null);
			                setGraphic(null);
			            }
	                    	
	                }
				};

	            cell.setOnDragDetected(new EventHandler<MouseEvent>() {
	                
	            	@Override
	                public void handle(MouseEvent mouseEvent) {
	                	String i = cell.getItem();
	                	Dragboard db = cell.startDragAndDrop(TransferMode.ANY);
	                    ClipboardContent content = new ClipboardContent();
	                    content.putString("Resource"+i);
	                    db.setContent(content);
	                    
	                    mouseEvent.consume();
	                }
	            });
	            
//	            cell.setOnDragEntered(new EventHandler<DragEvent>() {
//
//	            	@Override
//	            	public void handle(DragEvent event) {
//	                /* the drag-and-drop gesture entered the target */
//	                /* show to the user that it is an actual gesture target */
//	                     if (event.getGestureSource() != cell &&
//	                             event.getDragboard().hasString()) {
//	                         cell.setStyle("-fx-background-color: lightgrey");
//	                     }
//	                            
//	                     event.consume();
//	                }
//	            });
//	            
//	            cell.setOnDragExited(new EventHandler<DragEvent>() {
//
//	            	@Override
//	            	public void handle(DragEvent event) {
//	                /* the drag-and-drop gesture entered the target */
//	                /* show to the user that it is an actual gesture target */
//	                     if (event.getGestureSource() != cell &&
//	                             event.getDragboard().hasString()) {
//	                         cell.setStyle("-fx-background-color: white");
//	                     }
//	                            
//	                     event.consume();
//	                }
//	            });
//	            
//	            cell.setOnDragOver(new EventHandler<DragEvent>() {
//	            	
//	            	@Override
//	                public void handle(DragEvent event) {
//	                    /* data is dragged over the target */
//	                    /* accept it only if it is not dragged from the same node 
//	                     * and if it has a string data */
//	                    if (event.getGestureSource() != cell &&
//	                            event.getDragboard().hasString()) {
//	                        /* allow for both copying and moving, whatever user chooses */
//	                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//	                    }
//	                    event.consume();
//	                }
//	            });
	            
//	            cell.setOnDragDropped(new EventHandler<DragEvent>() {
//
//					@Override
//					public void handle(DragEvent event) {
//						if (event.getDragboard().hasString()) {
//							String message = event.getDragboard().getString();
//							if(message.contains("Resource")){
//								
//								EntityId childId= new EntityId(Long.parseLong(message.replace("EntityId", "")));
//								EventManager.post(new ParentingChangedEvent(childId, cell.getItem().entityId));
//							}
//						}
//					}
//				});
	            return cell;
	        }
	    });
        list.setOnDragOver(new EventHandler<DragEvent>() {
    	
    	@Override
        public void handle(DragEvent event) {
            if (event.getGestureSource() != list &&
                    event.getDragboard().hasString()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        }
    });

		list.setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				if (event.getDragboard().hasString()) {
					String message = event.getDragboard().getString();
					if(message.contains("EntityId")){
						EntityId childId= new EntityId(Long.parseLong(message.replace("EntityId", "")));
						EventManager.post(new SaveEntityEvent(childId));
					}
				}
			}
		});
	}
	

}
