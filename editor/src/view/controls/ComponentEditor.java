package view.controls;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.simsilica.es.EntityComponent;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import view.UIConfig;
import view.controls.propertyEditor.PropertyEditor;
import view.controls.propertyEditor.PropertyEditorFactory;

public class ComponentEditor extends TitledPane {

	BeanInfo bi = null;
	List<PropertyEditor> editors = new ArrayList<>();

	public ComponentEditor(EntityComponent comp) {
		expandedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue && !UIConfig.expandedComponents.contains(comp.getClass()))
					UIConfig.expandedComponents.add(comp.getClass());
				else
					UIConfig.expandedComponents.remove(comp.getClass());
			}
		});
		setExpanded(UIConfig.expandedComponents.contains(comp.getClass()));

		setAnimated(false);
		setText(comp.getClass().getSimpleName());
		VBox content = new VBox(); 
		setContent(content);
		BeanInfo bi = null;
		try {
			bi = Introspector.getBeanInfo(comp.getClass(), Object.class);
			for (PropertyDescriptor pd : bi.getPropertyDescriptors()) {
				PropertyEditor editor = PropertyEditorFactory.getEditorFor(comp, pd);
				if (editor != null){
					editors.add(editor);
					content.getChildren().add(editor);
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
	}
	
	public void updateComponent(EntityComponent comp){
		for(PropertyEditor editor : editors)
			editor.updateValue(comp);
	}
}
