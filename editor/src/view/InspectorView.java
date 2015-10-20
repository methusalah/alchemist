package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.simsilica.es.EntityComponent;

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
import javafx.scene.layout.VBox;
import model.EntityPresenter;
import model.ES.component.Naming;
import util.event.AddComponentEvent;
import util.event.EventManager;
import view.controls.ComponentEditor;

public class InspectorView extends VBox{
	List<String> compNames = null;
	VBox compControl;
	Label title;
	Label info;
	Button addCompButton;
	
	Map<Class<? extends EntityComponent>, ComponentEditor> editors = new HashMap<>();
	
	public InspectorView(ObjectProperty<EntityPresenter> selection) {
		selection.addListener(new ChangeListener<EntityPresenter>() {
			
			@Override
			public void changed(ObservableValue<? extends EntityPresenter> observable, EntityPresenter oldValue,
					EntityPresenter newValue) {
				inspectNewEntity(newValue);
				newValue.componentListProperty().addListener(new ListChangeListener<EntityComponent>() {
					
					@Override
					public void onChanged(Change<? extends EntityComponent> arg0) {
						inspectSameEntity(newValue);
					}
				});
				newValue.nameProperty().addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						info.setText("Name : "+newValue);
					}
				});
			}
		});
		
		setMinWidth(300);
		setMaxWidth(500);
		
		title = new Label("Inspector");
		title.setMinHeight(40);
		title.setMaxWidth(Double.MAX_VALUE);
		title.setStyle("-fx-background-color: lightblue");
		getChildren().add(title);
		
		info = new Label("");
		getChildren().add(info);
		
		compControl = new VBox();
		getChildren().add(compControl);
		
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
		getChildren().add(addCompButton);
	}
	
	private void inspectNewEntity(EntityPresenter ep){
		info.setText("");
		compControl.getChildren().clear();
		editors.clear();
		for(EntityComponent comp : ep.componentListProperty()){
			if(comp instanceof Naming)
				info.setText("Name : "+((Naming)comp).name);
			ComponentEditor editor = new ComponentEditor(comp);
			editors.put(comp.getClass(), editor);
			compControl.getChildren().add(editor);
		}
		addCompButton.setVisible(true);
	}
	
	public void setComponentNames(List<String> compNames){
		this.compNames = compNames;
	}
	
	private void inspectSameEntity(EntityPresenter ep){
		Map<Class<? extends EntityComponent>, ComponentEditor> unneededEditors = new HashMap<>(editors);
		
		for(EntityComponent comp : ep.componentListProperty()){
			ComponentEditor editor = editors.get(comp.getClass());
			if(editor != null){
				editor.updateComponent(comp);
				unneededEditors.remove(comp.getClass());
			} else {
				editor = new ComponentEditor(comp);
				editors.put(comp.getClass(), editor);
				compControl.getChildren().add(editor);
			}
		}
		compControl.getChildren().removeAll(unneededEditors.values());
		for(Class<? extends EntityComponent> compClass : unneededEditors.keySet()){
			editors.remove(compClass);
		}
	}
}
