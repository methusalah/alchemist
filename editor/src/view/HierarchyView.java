package view;

import java.util.List;

import com.sun.javafx.scene.control.skin.LabeledText;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.EntityNode;
import util.LogUtil;
import util.event.EntityCreationEvent;
import util.event.EntityDeletionEvent;
import util.event.EntitySelectionChanged;
import util.event.EventManager;
import view.controls.EntityNodeItem;
import view.controls.EntityTreeView;

public class HierarchyView extends VBox{
	EntityTreeView tree;

	public HierarchyView() {
		setPrefWidth(300);
		Label title = new Label("Hierarchy");
		title.setMinHeight(40);
		title.setMaxWidth(Double.MAX_VALUE);
		title.setStyle("-fx-background-color: lightblue");
		getChildren().add(title);

		// add button
		Button btnAdd = new Button("Add entity");
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				EventManager.post(new EntityCreationEvent());
			}
		});
		getChildren().add(btnAdd);

		// remove button
		Button btnRemove = new Button("Remove entity");
		btnRemove.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(tree != null && tree.getSelectionModel().getSelectedItem() != null)
				EventManager.post(new EntityDeletionEvent(tree.getSelectionModel().getSelectedItem().getValue().entityId));
			}
		});
		getChildren().add(btnRemove);
	}
	
	public void update(List<EntityNode> nodes){
		getChildren().remove(tree);
		tree = new EntityTreeView(nodes);
		getChildren().add(tree);
	}
}
