package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.simsilica.es.EntityComponent;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
import util.LogUtil;
import util.event.AddComponentEvent;
import util.event.EventManager;
import view.controls.ComponentEditor;

public class InspectorView extends VBox{
	List<String> compNames = null;
	VBox compControl;
	Label title;
	Label info;
	Button addCompButton;
	
	
	ListChangeListener<EntityComponent> listener = new ListChangeListener<EntityComponent>() {

		@Override
		public void onChanged(Change<? extends EntityComponent> arg0) {
			inspectSameEntity(arg0.getList());
		}
	};
	
	Map<Class<? extends EntityComponent>, ComponentEditor> editors = new HashMap<>();
	
	public InspectorView(ObjectProperty<EntityPresenter> selection) {
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
		compControl.getChildren().clear();
		editors.clear();
		LogUtil.info("inspect new entity");
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
	
	/**
	 * Update the component in the component editor to avoid editor to be recreated each time
	 * 
	 * Usefull for the value that are modified in real time like slider
	 * 
	 * @param ep
	 */
	private void inspectSameEntity(ObservableList<? extends EntityComponent> observableList){
		
		List<Class<? extends EntityComponent>> unneededEditorsclass = new ArrayList<>(editors.keySet());
		List<ComponentEditor> unneededEditors = new ArrayList<>(editors.values());
		
		for(EntityComponent comp : observableList){
			LogUtil.info("look for editor for "+c omp.getClass().getSimpleName());
			ComponentEditor editor = editors.get(comp.getClass());
			if(editor != null){
				editor.updateComponent(comp);
				unneededEditorsclass.remove(comp.getClass());
				unneededEditors.remove(editor);
			} else {
				editor = new ComponentEditor(comp);
				LogUtil.info("creation of editor for "+comp.getClass().getSimpleName());
				editors.put(comp.getClass(), editor);
				compControl.getChildren().add(editor);
			}
		}
		compControl.getChildren().removeAll(unneededEditors);
		for(Class<? extends EntityComponent> compClass : unneededEditorsclass){
			editors.remove(compClass);
			LogUtil.info("removing "+compClass.getSimpleName());
		}
//		LogUtil.info("inspect same entity");
	}
}
