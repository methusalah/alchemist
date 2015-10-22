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
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.Blueprint;
import model.EntityPresenter;
import util.LogUtil;
import util.event.EntityCreationFromBlueprintEvent;
import util.event.EntitySelectionChanged;
import util.event.EventManager;
import util.event.ParentingChangedEvent;
import view.Dragpool;

public class EntityTreeView extends TreeView<EntityPresenter> {
	
	public EntityTreeView(EntityPresenter rootPresenter) {
		getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<EntityPresenter>>() {

			@Override
			public void changed(ObservableValue<? extends TreeItem<EntityPresenter>> observable, TreeItem<EntityPresenter> oldValue, TreeItem<EntityPresenter> newValue) {
				if(newValue != null && newValue.getValue() != null)
					EventManager.post(new EntitySelectionChanged(newValue.getValue()));
			}
		});
		
		EntityNodeItem root = new EntityNodeItem(rootPresenter);
		setShowRoot(false);
		for(EntityPresenter n : rootPresenter.childrenListProperty())
			addItem(root, n);
		setRoot(root);
		configureCellFactoryForDragAndDrop();
	}
	
	private void addItem(EntityNodeItem parent, EntityPresenter ep){
		EntityNodeItem i = new EntityNodeItem(ep);
		parent.getChildren().add(i);
		for(EntityPresenter childNode : ep.childrenListProperty()){
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
	            		Dragpool.setContent(cell.getItem());
	                	
	                	Dragboard db = cell.startDragAndDrop(TransferMode.ANY);
	                    ClipboardContent content = new ClipboardContent();
	                    content.putString("");
	                    db.setContent(content);
	                    
	                    mouseEvent.consume();
	                }
	            });
	            
	            cell.setOnDragEntered(new EventHandler<DragEvent>() {

	            	@Override
	            	public void handle(DragEvent event) {
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
	                     if (event.getGestureSource() != cell &&
	                             event.getDragboard().hasString()) {
	                         cell.setStyle(null);
	                     }
	                            
	                     event.consume();
	                }
	            });
	            
	            
	            cell.setOnDragOver(new EventHandler<DragEvent>() {
	            	
	            	@Override
	                public void handle(DragEvent event) {
	                    if (event.getGestureSource() != cell && !Dragpool.isEmpty()) {
                    		event.acceptTransferModes(TransferMode.ANY);
	                    }
	                    event.consume();
	                }
	            });
	            
	            
	            cell.setOnDragDropped(new EventHandler<DragEvent>() {

					@Override
					public void handle(DragEvent event) {
						if(!Dragpool.isEmpty()) {
	                    	if(Dragpool.containsType(EntityPresenter.class)){
								EventManager.post(new ParentingChangedEvent(Dragpool.grabContent(EntityPresenter.class), cell.getItem()));
							} else if(Dragpool.containsType(Blueprint.class)){
								EventManager.post(new EntityCreationFromBlueprintEvent(Dragpool.grabContent(Blueprint.class), cell.getItem()));
							}
						}
					}
				});
	            return cell;
	        }
	    });
	}
}
