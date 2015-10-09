package view;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;



import com.simsilica.es.EntityComponent;

import application.MainEditor;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.ES.richData.ColorData;
import model.ES.richData.PhysicStat;
import model.ES.serial.Blueprint;
import util.LogUtil;
import view.controls.PropertyEditor;
import view.controls.PropertyEditorFactory;

public class InspectorController {
	@FXML
	private Label title;
	@FXML
	private VBox vbox;
	
	@FXML
    private void initialize() {
		
	}
	
	public void setBlueprint(Blueprint blueprint){
		title.setText("Inspector");
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
		vbox.setPadding(new Insets(5));
		compPane.setContent(compDetail);
		BeanInfo bi = null;
		try {
			bi = Introspector.getBeanInfo(comp.getClass(), Object.class);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		for(PropertyDescriptor pd : bi.getPropertyDescriptors()){
			PropertyEditor editor = PropertyEditorFactory.getEditorFor(comp, pd);
			if(editor != null)
				compDetail.getChildren().add(editor);
		}
			
//		for(Field field : comp.getClass().getFields()){
//			compDetail.getChildren().add(getFieldEditor(comp, field));
//		}

		return compPane;
	}
	
	private Node getFieldEditor(EntityComponent comp, Field f){
		FXMLLoader l = new FXMLLoader();
		l.setLocation(MainEditor.class.getResource("/view/FieldEditor.fxml"));
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
