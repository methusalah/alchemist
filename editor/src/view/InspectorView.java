package view;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;
import com.simsilica.es.EntityComponent;
import com.sun.java_cup.internal.runtime.virtual_parse_stack;

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
import view.controls.propertyEditor.PropertyEditor;
import view.controls.propertyEditor.PropertyEditorFactory;

public class InspectorView extends VBox{
	VBox compControl;
	
	public InspectorView() {
		setPrefWidth(400);
		Label title = new Label("Inspector");
		title.setMinHeight(40);
		title.setMaxWidth(Double.MAX_VALUE);
		title.setStyle("-fx-background-color: lightblue");
		getChildren().add(title);
		
		compControl = new VBox();
		getChildren().add(compControl);
	}
	
	public void loadComponents(List<EntityComponent> comps){
		compControl.getChildren().clear();
		for(EntityComponent comp : comps){
			compControl.getChildren().add(getComponentEditor(comp));
			LogUtil.info("load comp "+comps);
		}
	}
	
	private TitledPane getComponentEditor(EntityComponent comp){
		TitledPane compPane = new TitledPane();
		compPane.setExpanded(false);
		compPane.setAnimated(false);
		compPane.setText(comp.getClass().getSimpleName());
		VBox compDetail = new VBox();
		compPane.setContent(compDetail);
		BeanInfo bi = null;
		try {
			bi = Introspector.getBeanInfo(comp.getClass(), Object.class);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		LogUtil.info("comp " + comp);
		for(PropertyDescriptor pd : bi.getPropertyDescriptors()){
			PropertyEditor editor = PropertyEditorFactory.getEditorFor(comp, pd);
			if(editor != null)
				compDetail.getChildren().add(editor);
		}
		return compPane;
		
	}
	
}
