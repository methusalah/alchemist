package view.controls;

import java.util.List;

import com.simsilica.es.EntityId;

import util.LogUtil;
import util.event.EntitySelectionChanged;
import util.event.EventManager;
import util.event.ParentingChangedEvent;
import view.UIConfig;
import model.EntityNode;
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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class EntityTreeView extends TreeView<EntityNode> {
	EntityNodeItem toSelect;

	public EntityTreeView(List<EntityNode> nodes) {
		getSelectionModel().selectedItemProperty().addListener( new ChangeListener<TreeItem<EntityNode>>() {

			@Override
			public void changed(ObservableValue<? extends TreeItem<EntityNode>> observable, TreeItem<EntityNode> oldValue, TreeItem<EntityNode> newValue) {
				if(newValue.getValue() != null)
					EventManager.post(new EntitySelectionChanged(newValue.getValue().parent));
				UIConfig.selectedEntityNode = newValue.getValue();
			}
		});
		
		setCellFactory(new Callback<TreeView<EntityNode>, TreeCell<EntityNode>>() {
	        @Override
	        public TreeCell<EntityNode> call(TreeView<EntityNode> stringTreeView) {
	            TreeCell<EntityNode> cell = new TreeCell<EntityNode>() {
	                protected void updateItem(EntityNode item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (item != null) {
	                        setText(item.toString());
	                    }
	                }
	            };

	            cell.setOnDragDetected(new EventHandler<MouseEvent>() {
	                @Override
	                public void handle(MouseEvent mouseEvent) {
	                	EntityNode i = cell.getItem();
	                	
	                	Dragboard db = cell.startDragAndDrop(TransferMode.ANY);
	                    ClipboardContent content = new ClipboardContent();
	                    content.putString("EntityId"+i.parent.getId());
	                    db.setContent(content);
	                    
	                    mouseEvent.consume();
	                }
	            });
	            
	            cell.setOnDragEntered(new EventHandler<DragEvent>() {
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
								EventManager.post(new ParentingChangedEvent(childId, cell.getItem().parent));
							}
						}
					}
				});

	            return cell;
	        }
	    });
		
		EntityNodeItem root = new EntityNodeItem(null);
		root.setExpanded(true);
		toSelect = null;
		for(EntityNode n : nodes)
			addItem(root, n);
		setRoot(root);
		if(toSelect != null)
			getSelectionModel().select(toSelect);
	}
	
	private void addItem(EntityNodeItem parent, EntityNode node){
		EntityNodeItem i = new EntityNodeItem(node);
		parent.getChildren().add(i);
		if(node == UIConfig.selectedEntityNode)
			toSelect = i;
		for(EntityNode childNode : node.children){
			addItem(i, childNode);
		}
	}
}
