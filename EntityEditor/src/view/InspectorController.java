package view;

import java.lang.reflect.Field;

import com.simsilica.es.EntityComponent;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.ES.serial.Blueprint;

public class InspectorController {
	@FXML
	private Label title;
//	@FXML
//	private VBox root;
	
	public Pane root;
	
	@FXML
    private void initialize() {
		
	}
	
	public void setBlueprint(Blueprint blueprint){
		((Label)root.lookup("#title")).setText("inspector");
		for(EntityComponent comp : blueprint.getComps()){
			TitledPane compPane = new TitledPane();
			compPane.setExpanded(false);
			compPane.setText(comp.getClass().getSimpleName());
			VBox compDetail = new VBox();
			compPane.setContent(compDetail);
			for(Field field : comp.getClass().getFields()){
				Pane fieldPane = new Pane();
				fieldPane.getChildren().add(new Label(field.getName()));
				compDetail.getChildren().add(fieldPane);
			}
			root.getChildren().add(compPane);
		}
	}

}
