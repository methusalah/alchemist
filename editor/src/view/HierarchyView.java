package view;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.EntityPresenter;
import util.event.EntityCreationEvent;
import util.event.EntityDeletionEvent;
import util.event.EventManager;
import view.controls.EntityTreeView;

public class HierarchyView extends VBox{
	EntityTreeView tree;

	public HierarchyView() {
		setMinWidth(300);
		setMaxHeight(Double.MAX_VALUE);
		setPadding(new Insets(3));
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
				EventManager.post(new EntityDeletionEvent(tree.getSelectionModel().getSelectedItem().getValue().getEntityId()));
			}
		});
		getChildren().add(btnRemove);
	}
	
	public void update(List<EntityPresenter> nodes){
		getChildren().remove(tree);
		tree = new EntityTreeView(nodes);
		tree.setMaxHeight(Double.MAX_VALUE);
		getChildren().add(tree);
	}
}
