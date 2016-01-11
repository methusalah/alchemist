package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.simsilica.es.EntityComponent;

import application.EditorPlatform;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import presenter.EntityNode;
import presenter.InspectorPresenter;
import util.LogUtil;
import view.controls.ComponentEditor;

public class InspectorTab extends Tab {
	final InspectorPresenter presenter;
	VBox compControl;
	Label info;
	Button addCompButton;
	
	ListChangeListener<EntityComponent> listener = e -> {
		while(e.next())
			if(e.wasReplaced())
				for(EntityComponent comp : e.getAddedSubList())
					updateComponent(comp);
			else if(e.wasAdded())
				for(EntityComponent comp : e.getAddedSubList())
					addComponent(comp);
			else if(e.wasRemoved())
				for(EntityComponent comp : e.getRemoved())
					removeComponent(comp);
	};
	
	Map<Class<? extends EntityComponent>, ComponentEditor> editors = new HashMap<>();
	
	public InspectorTab() {
		presenter = new InspectorPresenter();
		
		setText("Inspector");
		setClosable(false);
		EditorPlatform.getSelectionProperty().addListener((observable, oldValue, newValue) -> {
			inspectNewEntity(newValue);
			if(oldValue != null)
				oldValue.componentListProperty().removeListener(listener);
			newValue.componentListProperty().addListener(listener);
			info.textProperty().bind(newValue.nameProperty());
		});
		
		VBox content = new VBox();
		
		info = new Label("");
		content.getChildren().add(info);
		
		compControl = new VBox();
		content.getChildren().add(compControl);
		
		addCompButton = new Button("Add component");
		addCompButton.setVisible(false);
		addCompButton.setOnAction(e -> {
			if(presenter.getComponentNames().isEmpty()){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("There is no component available.");
			} else {
				ChoiceDialog<String> dialog = new ChoiceDialog<>(presenter.getComponentNames().get(0), presenter.getComponentNames());
				dialog.setTitle("Component choice".toUpperCase());
				dialog.setHeaderText(null);
				dialog.setContentText("Choose the component in the list :");

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				result.ifPresent(compName -> presenter.addComponent(compName));
			}
		});
		content.getChildren().add(addCompButton);
		
		setContent(content);
	}
	
	private void inspectNewEntity(EntityNode ep){
		compControl.getChildren().clear();
		editors.clear();
		for(EntityComponent comp : ep.componentListProperty()){
			LogUtil.info("    comp : "+comp.getClass().getSimpleName()	);
			ComponentEditor editor = new ComponentEditor(presenter, comp);
			editors.put(comp.getClass(), editor);
			compControl.getChildren().add(editor);
		}
		addCompButton.setVisible(true);
	}
	
	private void updateComponent(EntityComponent comp){
		ComponentEditor editor = editors.get(comp.getClass());
		if(editor.isExpanded())
			editor.updateComponent(comp);
	}
	
	private void addComponent(EntityComponent comp){
		ComponentEditor editor = new ComponentEditor(presenter, comp);
		editors.put(comp.getClass(), editor);
		compControl.getChildren().add(editor);
	}
	
	private void removeComponent(EntityComponent comp){
		ComponentEditor editor = editors.get(comp.getClass());
		editors.remove(comp.getClass());
		compControl.getChildren().remove(editor);
	}
}
