package view;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.eventbus.Subscribe;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;
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
import model.EntityNode;
import model.ES.component.Naming;
import model.ES.richData.ColorData;
import model.ES.richData.PhysicStat;
import model.ES.serial.Blueprint;
import util.LogUtil;
import view.controls.propertyEditor.PropertyEditor;
import view.controls.propertyEditor.PropertyEditorFactory;

public class InspectorView extends VBox{
	VBox compControl;
	Label title;
	Label info;
	
	private EntityId eid;
	private Map<EntityComponent, TitledPane>
	
	private Map<Class<? extends EntityComponent>, Boolean> expandMemory = new HashMap<>();
	
	public InspectorView() {
		setPrefWidth(400);
		title = new Label("Inspector");
		title.setMinHeight(40);
		title.setMaxWidth(Double.MAX_VALUE);
		title.setStyle("-fx-background-color: lightblue");
		getChildren().add(title);
		
		info = new Label("");
		getChildren().add(info);
		
		compControl = new VBox();
		getChildren().add(compControl);
	}
	
	public void loadComponents(EntityId eid, List<EntityComponent> comps){
		
		info.setText("");
		compControl.getChildren().clear();
		for(EntityComponent comp : comps){
			if(comp instanceof Naming)
				info.setText("Name : "+((Naming)comp).name);
			if(this.eid == eid)
				
			else
				compControl.getChildren().add(getComponentEditor(comp));
			LogUtil.info("load comp "+comps);
		}
		
		this.eid = eid;
	}
	
	private TitledPane getComponentEditor(EntityComponent comp){
		TitledPane compPane = new TitledPane();
		
		// expand memory
		compPane.expandedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		        expandMemory.put(comp.getClass(), newValue);
		    }
		});
		if(!expandMemory.containsKey(comp.getClass()))
			expandMemory.put(comp.getClass(), false);
		compPane.setExpanded(expandMemory.get(comp.getClass()));

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
	
	public void updateEntityName(String name){
		info.setText("Name : "+name);
		
	}
	
}
