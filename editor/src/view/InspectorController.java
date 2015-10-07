package view;

import java.io.IOException;
import java.lang.reflect.Field;

import com.simsilica.es.EntityComponent;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import model.ES.serial.Blueprint;
import util.LogUtil;

public class InspectorController {
	@FXML
	private Label title;
	@FXML
	private VBox vbox;
	
	@FXML
    private void initialize() {
		
	}
	
	public void setBlueprint(Blueprint blueprint){
		title.setText("inspector");
		for(EntityComponent comp : blueprint.getComps()){
			vbox.getChildren().add(getComponentEditor(comp));
		}
	}
	
	private Node getComponentEditor(EntityComponent comp){
		
		TitledPane compPane = new TitledPane();
		compPane.setExpanded(false);
		compPane.setAnimated(false);
		compPane.setText(comp.getClass().getSimpleName());
		VBox compDetail = new VBox();
		compPane.setContent(compDetail);
		for(Field field : comp.getClass().getFields()){
			compDetail.getChildren().add(getFieldEditor(comp, field));
		}
		return compPane;
	}
	
	private Node getFieldEditor(EntityComponent comp, Field f){
		FXMLLoader l = new FXMLLoader();
		l.setLocation(Main.class.getResource("/view/FieldEditor.fxml"));
		try {
			l.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FieldEditorCtrl ctrl = l.getController();
		
		ctrl.setField(comp, f);
		return l.getRoot();
	}
	

}
