package view;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import org.controlsfx.control.PropertySheet;
import org.controlsfx.control.PropertySheet.Item;
import org.controlsfx.property.BeanProperty;
import org.controlsfx.property.BeanPropertyUtils;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import org.controlsfx.property.editor.DefaultPropertyEditorFactory;
import org.controlsfx.property.editor.Editors;
import org.controlsfx.property.editor.PropertyEditor;

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
//		VBox compDetail = new VBox();
//		vbox.setPadding(new Insets(5));
//		compPane.setContent(compDetail);
//		for(Field field : comp.getClass().getFields()){
//			compDetail.getChildren().add(getFieldEditor(comp, field));
//		}

		
		
		
        ObservableList<Item> list = FXCollections.observableArrayList();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(comp.getClass(), Object.class);
            for (PropertyDescriptor p : beanInfo.getPropertyDescriptors()) {
                    list.add(new ComponentProperty(comp, p));
                }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        
		PropertySheet sheet = new PropertySheet(list);
		
		SimpleObjectProperty<Callback<PropertySheet.Item, PropertyEditor<?>>> propertyEditorFactory = new SimpleObjectProperty<>(this, "propertyEditor", new DefaultPropertyEditorFactory());
		sheet.setPropertyEditorFactory(new Callback<PropertySheet.Item, PropertyEditor<?>>() {
		    @Override
		    public PropertyEditor<?> call(PropertySheet.Item param) {
		        if(param.getValue() instanceof ColorData) {
		        	return new AbstractPropertyEditor<ColorData, ColorPicker>(param, new ColorPicker()) {

		                @Override protected ObservableValue<ColorData> getObservableValue() {
		                	Color c = getEditor().valueProperty().getValue();
		                	ColorData cd = new ColorData((int)Math.round(c.getOpacity()*255),
		                			(int)Math.round(c.getRed()),
		                			(int)Math.round(c.getGreen()),
		                			(int)Math.round(c.getBlue()));
		                	return getObservableValue(cd);
		                }

		                @Override public void setValue(ColorData value) {
		                    getEditor().setValue((ColorData) value);
		                }
		            };		        }

		        return propertyEditorFactory.get().call(param);
		    }
		});
		
		sheet.setModeSwitcherVisible(false);
		sheet.setSearchBoxVisible(false);
		compPane.setContent(sheet);

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
