package view.controls;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;
import model.EntityPresenter;
import util.event.EntitySelectionChanged;
import util.event.EventManager;
import util.event.ParentingChangedEvent;
import view.UIConfig;

import com.simsilica.es.EntityId;

public class EntityTreeView extends TreeView<EntityPresenter> {
	EntityNodeItem toSelect;

	public EntityTreeView(EntityPresenter rootPresenter) {
		getSelectionModel().selectedItemProperty().addListener( new ChangeListener<TreeItem<EntityPresenter>>() {

			@Override
			public void changed(ObservableValue<? extends TreeItem<EntityPresenter>> observable, TreeItem<EntityPresenter> oldValue, TreeItem<EntityPresenter> newValue) {
				if(newValue.getValue() != null)
					EventManager.post(new EntitySelectionChanged(newValue.getValue()));
				UIConfig.selectedEntityNode = newValue.getValue();
			}
		});
		
		configureCellFactoryForDragAndDrop();
		
		EntityNodeItem root = new EntityNodeItem(rootPresenter);
		root.setExpanded(true);
		toSelect = null;
		for(EntityPresenter n : rootPresenter.childrenListProperty())
			addItem(root, n);
		setRoot(root);
		if(toSelect != null)
			getSelectionModel().select(toSelect);
	}
	
	private void addItem(EntityNodeItem parent, EntityPresenter node){
		EntityNodeItem i = new EntityNodeItem(node);
		parent.getChildren().add(i);
		if(node == UIConfig.selectedEntityNode)
			toSelect = i;
		for(EntityPresenter childNode : node.childrenListProperty()){
			addItem(i, childNode);
		}
	}
	
	private void configureCellFactoryForDragAndDrop(){
		setCellFactory(new Callback<TreeView<EntityPresenter>, TreeCell<EntityPresenter>>() {

			@Override
	        public TreeCell<EntityPresenter> call(TreeView<EntityPresenter> stringTreeView) {
	            TreeCell<EntityPresenter> cell = new TreeCell<EntityPresenter>() {
	            	
	            	@Override
	                protected void updateItem(EntityPresenter item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (item != null) {
	                    	textProperty().bind(item.nameProperty());
	                    } else {
	                    	textProperty().unbind();
	                        setText(null);
			                setGraphic(null);
			            }
	                    	
	                }
	            };

	            cell.setOnDragDetected(new EventHandler<MouseEvent>() {
	                
	            	@Override
	                public void handle(MouseEvent mouseEvent) {
	                	EntityPresenter i = cell.getItem();
	                	
	                	Dragboard db = cell.startDragAndDrop(TransferMode.ANY);
	                    ClipboardContent content = new ClipboardContent();
	                    content.putString("EntityId"+i.getEntityId().getId());
	                    db.setContent(content);
	                    
	                    mouseEvent.consume();
	                }
	            });
	            
	            cell.setOnDragEntered(new EventHandler<DragEvent>() {

	            	@Override
	            	public void handle(DragEvent event) {
	                /* the drag-and-drop gesture entered the target */
	                /* show to the user that it is an actual gesture target */
	                     if (event.getGestureSource() != cell &&
	                             event.getDragboard().hasString()) {
	                         cell.setStyle("-fx-background-color: lightgrey");
	                     }
	                            
	                     event.consume();
	                }
	            });
	            cell.setOnDragExited(new EventHandler<DragEvent>() {

	            	@Override
	            	public void handle(DragEvent event) {
	                /* the drag-and-drop gesture entered the target */
	                /* show to the user that it is an actual gesture target */
	                     if (event.getGestureSource() != cell &&
	                             event.getDragboard().hasString()) {
	                         cell.setStyle("-fx-background-color: white");
	                     }
	                            
	                     event.consume();
	                }
	            });
	            
	            
	            cell.setOnDragOver(new EventHandler<DragEvent>() {
	            	
	            	@Override
	                public void handle(DragEvent event) {
	                    /* data is dragged over the target */
	                    /* accept it only if it is not dragged from the same node 
	                     * and if it has a string data */
	                    if (event.getGestureSource() != cell &&
	                            event.getDragboard().hasString()) {
	                        /* allow for both copying and moving, whatever user chooses */
	                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
	                    }
	                    event.consume();
	                }
	            });
	            
	            
	            cell.setOnDragDropped(new EventHandler<DragEvent>() {

					@Override
					public void handle(DragEvent event) {
						if (event.getDragboard().hasString()) {
							String message = event.getDragboard().getString();
							if(message.contains("EntityId")){
								EntityId childId= new EntityId(Long.parseLong(message.replace("EntityId", "")));
								EventManager.post(new ParentingChangedEvent(childId, cell.getItem().getEntityId()));
							}
						}
					}
				});
	            return cell;
	        }
	    });
	}
}
