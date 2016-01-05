package view.controls;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import presenter.InspectorPresenter;

import com.simsilica.es.EntityComponent;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.ES.component.world.TerrainTooling;
import util.event.EventManager;
import util.event.RemoveComponentEvent;
import view.UIConfig;
import view.WorldEditorTab;
import view.controls.propertyEditor.PropertyEditor;
import view.controls.propertyEditor.PropertyEditorFactory;

public class ComponentEditor extends TitledPane {
	final InspectorPresenter presenter;
	final EntityComponent comp;

	final List<PropertyEditor> editors = new ArrayList<>();

	public ComponentEditor(InspectorPresenter presenter, EntityComponent comp) {
		this.comp = comp;
		this.presenter = presenter;
		expandedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue && !UIConfig.expandedComponents.contains(comp.getClass()))
				UIConfig.expandedComponents.add(comp.getClass());
			else
				UIConfig.expandedComponents.remove(comp.getClass());
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
				PropertyEditor editor = PropertyEditorFactory.getEditorFor(presenter, comp, pd);
				if (editor != null) {
					editors.add(editor);
					content.getChildren().add(editor);
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		
		createOptions();
	}

	public void updateComponent(EntityComponent comp) {
		for (PropertyEditor editor : editors)
			editor.updateValue(comp);
	}

	private void createOptions(){
		Label label = new Label();  
        label.textProperty().bind(textProperty());
        
        BorderPane bp = new BorderPane();
		bp.setMaxWidth(Double.MAX_VALUE);

		Button btn = new Button("remove");
		btn.setMaxWidth(Double.MAX_VALUE);
		btn.setOnAction(e -> presenter.removeComponent(comp.getClass()));
		
		bp.setLeft(label);
		bp.setRight(btn);
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		setGraphic(bp);
		
        bp.prefWidthProperty().bind(new DoubleBinding() {  
            {  
                super.bind(widthProperty());  
            }  
  
            @Override  
            protected double computeValue() {  
                double breathingSpace = 40 ;  
                double value = getWidth() - breathingSpace ;  
                return value;  
            }  
        });  
	}
}
