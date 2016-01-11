package view.controls;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import model.ES.serial.Blueprint;
import presenter.EntityNode;
import presenter.HierarchyPresenter;
import view.Dragpool;

public class EntityTreeView extends TreeView<EntityNode> {
	final HierarchyPresenter presenter;
	
	public EntityTreeView(HierarchyPresenter presenter) {
		this.presenter = presenter;
		EntityNodeItem root = new EntityNodeItem(presenter.getRootNode());
		setShowRoot(false);
		for(EntityNode n : presenter.getRootNode().childrenListProperty())
			addItem(root, n);
		setRoot(root);
		configureCellFactoryForDragAndDrop();
	}
	
	private void addItem(EntityNodeItem parent, EntityNode ep){
		EntityNodeItem i = new EntityNodeItem(ep);
		parent.getChildren().add(i);
		for(EntityNode childNode : ep.childrenListProperty()){
			addItem(i, childNode);
		}
	}
	
	private void configureCellFactoryForDragAndDrop(){
		setCellFactory(callback ->{
            TreeCell<EntityNode> cell = new TreeCell<EntityNode>() {

            	@Override
                protected void updateItem(EntityNode item, boolean empty) {
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
                	if(Dragpool.containsType(EntityNode.class))
                		presenter.updateParenting(Dragpool.grabContent(EntityNode.class), cell.getItem());
					else if(Dragpool.containsType(Blueprint.class))
						presenter.createNewEntity(Dragpool.grabContent(Blueprint.class), cell.getItem());
				}
			});
            return cell;
	    });
	}
}
