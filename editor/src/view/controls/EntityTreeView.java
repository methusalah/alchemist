package view.controls;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;
import model.EntityPresenter;
import model.ES.serial.Blueprint;
import util.event.EntityCreationFromBlueprintEvent;
import util.event.EntitySelectionChanged;
import util.event.EventManager;
import util.event.ParentingChangedEvent;
import view.Dragpool;

public class EntityTreeView extends TreeView<EntityPresenter> {
	
	public EntityTreeView(EntityPresenter rootPresenter) {
		getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue != null && newValue.getValue() != null)
				EventManager.post(new EntitySelectionChanged(newValue.getValue()));
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
		setCellFactory(callback ->{
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
            
            cell.setOnDragDetected(e -> {
        		Dragpool.setContent(cell.getItem());
            	Dragboard db = cell.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString("");
                db.setContent(content);
                e.consume();
            });
            
            cell.setOnDragEntered(e -> {
                 if (e.getGestureSource() != cell && e.getDragboard().hasString()) 
                     cell.setStyle("-fx-background-color: lightgrey");
                 e.consume();
            });
            
            cell.setOnDragExited(e -> {
                     if (e.getGestureSource() != cell &&
                             e.getDragboard().hasString()) {
                         cell.setStyle(null);
                     }
                     e.consume();
            });
            
            
            cell.setOnDragOver(e -> {
                    if (e.getGestureSource() != cell && !Dragpool.isEmpty()) {
                		e.acceptTransferModes(TransferMode.ANY);
                    }
                    e.consume();
            });
            
            
            cell.setOnDragDropped(e -> {
				if(!Dragpool.isEmpty()) {
                	if(Dragpool.containsType(EntityPresenter.class))
						EventManager.post(new ParentingChangedEvent(Dragpool.grabContent(EntityPresenter.class), cell.getItem()));
					else if(Dragpool.containsType(Blueprint.class))
						EventManager.post(new EntityCreationFromBlueprintEvent(Dragpool.grabContent(Blueprint.class), cell.getItem()));
				}
			});
            return cell;
	    });
	}
}
