package view;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

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
		getChildren().add(list);
	}
	
	public void setBlueprints(List<String> blueprintsName){
		list.setItems(FXCollections.observableArrayList(blueprintsName));
	}
	
	
	

}
