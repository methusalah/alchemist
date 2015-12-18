package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import model.EntityPresenter;
import util.LogUtil;
import util.event.AddComponentEvent;
import util.event.EventManager;
import view.controls.ComponentEditor;

import com.simsilica.es.EntityComponent;

public class InspectorTab extends Tab {
	List<String> compNames = null;
	VBox compControl;
	Label info;
	Button addCompButton;
	
	
	ListChangeListener<EntityComponent> listener = new ListChangeListener<EntityComponent>() {

		@Override
		public void onChanged(Change<? extends EntityComponent> change) {
			while(change.next())
				if(change.wasReplaced())
					for(EntityComponent comp : change.getAddedSubList())
						updateComponent(comp);
				else if(change.wasAdded())
					for(EntityComponent comp : change.getAddedSubList())
						addComponent(comp);
				else if(change.wasRemoved())
					for(EntityComponent comp : change.getRemoved())
						removeComponent(comp);
		}
	};
	
	Map<Class<? extends EntityComponent>, ComponentEditor> editors = new HashMap<>();
	
	public InspectorTab(ObjectProperty<EntityPresenter> selection) {
		setText("Inspector");
		setClosable(false);
		selection.addListener(new ChangeListener<EntityPresenter>() {
			
			@Override
			public void changed(ObservableValue<? extends EntityPresenter> observable, EntityPresenter oldValue, EntityPresenter newValue) {
				inspectNewEntity(newValue);
				if(oldValue != null)
					oldValue.componentListProperty().removeListener(listener);
				newValue.componentListProperty().addListener(listener);
				info.textProperty().bind(newValue.nameProperty());
			}
		});
		
		VBox content = new VBox();
		
		info = new Label("");
		content.getChildren().add(info);
		
		compControl = new VBox();
		content.getChildren().add(compControl);
		
		addCompButton = new Button("Add component");
		addCompButton.setVisible(false);
		addCompButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(compNames == null || compNames.isEmpty()){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Error");
					alert.setHeaderText(null);
					alert.setContentText("There is no component available.");
				} else {
					ChoiceDialog<String> dialog = new ChoiceDialog<>(compNames.get(0), compNames);
					dialog.setTitle("Component choice".toUpperCase());
					dialog.setHeaderText(null);
					dialog.setContentText("Choose the component in the list :");

					// Traditional way to get the response value.
					Optional<String> result = dialog.showAndWait();
					result.ifPresent(compName -> EventManager.post(new AddComponentEvent(compName)));
				}
			}
		});
		content.getChildren().add(addCompButton);
		
		setContent(content);
	}
	
	private void inspectNewEntity(EntityPresenter ep){
		compControl.getChildren().clear();
		editors.clear();
		for(EntityComponent comp : ep.componentListProperty()){
			LogUtil.info("    comp : "+comp.getClass().getSimpleName()	);
			ComponentEditor editor = new ComponentEditor(comp);
			editors.put(comp.getClass(), editor);
			compControl.getChildren().add(editor);
		}
		addCompButton.setVisible(true);
	}
	
	public void setComponentNames(List<String> compNames){
		this.compNames = compNames;
	}
	

	private void updateComponent(EntityComponent comp){
		ComponentEditor editor = editors.get(comp.getClass());
		if(editor.isExpanded())
			editor.updateComponent(comp);
	}
	
	private void addComponent(EntityComponent comp){
		ComponentEditor editor = new ComponentEditor(comp);
		editors.put(comp.getClass(), editor);
		compControl.getChildren().add(editor);
	}
	
	private void removeComponent(EntityComponent comp){
		ComponentEditor editor = editors.get(comp.getClass());
		editors.remove(comp.getClass());
		compControl.getChildren().remove(editor);
	}
	
}
