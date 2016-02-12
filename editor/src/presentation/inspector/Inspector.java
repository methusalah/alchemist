package presentation.inspector;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simsilica.es.EntityComponent;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import presentation.util.ViewLoader;
import presenter.EditorPlatform;
import presenter.common.EntityNode;
import util.LogUtil;
import view.InspectorTab;

public class Inspector extends BorderPane {

	Map<Class<? extends EntityComponent>, ComponentEditor> editors = new HashMap<>();

	@FXML
	VBox componentBox;
	
	@FXML
	Label infoLabel;
	
	@FXML
	Button addButton;

	public Inspector() {
		ViewLoader.loadFXMLForControl(this);
		
		EditorPlatform.getSelectionProperty().addListener((observable, oldValue, newValue) -> {
			inspectNewEntity(newValue);
			if(oldValue != null)
				oldValue.componentListProperty().removeListener(listener);
			if(newValue != null)
				newValue.componentListProperty().addListener(listener);
		});
	}
	
	
	// REGION : View
	public void inspectNewEntity(EntityNode ep){
		componentBox.getChildren().clear();
		editors.clear();
		if(ep == null){
			infoLabel.textProperty().unbind();
			infoLabel.setText("No entity selected");
			return;
		}
		for(EntityComponent comp : ep.componentListProperty()){
			ComponentEditor editor = new ComponentEditor(comp, compClass -> removeComponent(compClass), (c, propertyName, value) -> updateComponent(c, propertyName, value));
			editors.put(comp.getClass(), editor);
			componentBox.getChildren().add(editor);
		}
		infoLabel.textProperty().bind(ep.nameProperty());
		addButton.setVisible(true);
	}
	
	public void updateComponentEditor(EntityComponent comp){
		ComponentEditor editor = editors.get(comp.getClass());
		if(editor.isExpanded())
			editor.updateComponent(comp);
	}
	
	public void addComponentEditor(EntityComponent comp){
		ComponentEditor editor = new ComponentEditor(comp, compClass -> removeComponent(compClass), (c, propertyName, value) -> updateComponent(c, propertyName, value));
		editors.put(comp.getClass(), editor);
		componentBox.getChildren().add(editor);
	}
	
	public void removeComponentEditor(EntityComponent comp){
		ComponentEditor editor = editors.get(comp.getClass());
		editors.remove(comp.getClass());
		componentBox.getChildren().remove(editor);
	}
	
	public void showComponentChooser(){
		ChoiceDialog<String> dialog = new ChoiceDialog<>(getComponentNames().get(0), getComponentNames());
		dialog.setTitle("Component choice".toUpperCase());
		dialog.setHeaderText(null);
		dialog.setContentText("Choose the component in the list :");
		Optional<String> result = dialog.showAndWait();
		result.ifPresent(compName -> addComponent(compName));
	}
	
	
	// REGION : presentation
	
	ListChangeListener<EntityComponent> listener = e -> {
		while(e.next())
			if(e.wasReplaced())
				for(EntityComponent comp : e.getAddedSubList())
					updateComponentEditor(comp);
			else if(e.wasAdded())
				for(EntityComponent comp : e.getAddedSubList())
					addComponentEditor(comp);
			else if(e.wasRemoved())
				for(EntityComponent comp : e.getRemoved())
					removeComponentEditor(comp);
	};
	
	public void updateComponent(EntityComponent comp, String propertyName, Object value){
		// In this piece of code, we can't just change the component's field because it's imutable
		// we serialize the whole component into a jsontree, change the value in the tree, then
		// deserialize in a new component, that we will be able to attach to entity the proper way
		ObjectMapper mapper = new ObjectMapper();
		JsonNode n = mapper.valueToTree(EditorPlatform.getEntityData().getComponent(EditorPlatform.getSelectionProperty().getValue().getEntityId(), comp.getClass()));
		Iterator<Entry<String, JsonNode>> i = n.fields();
		while (i.hasNext()) {
			Entry<String, JsonNode> entry = (Entry<String, JsonNode>) i.next();
			if(entry.getKey().equals(propertyName)){
				entry.setValue(mapper.valueToTree(value));
				break;
			}
		}
		
		EntityComponent newComp = null;
		try {
			newComp = new ObjectMapper().treeToValue(n, comp.getClass());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
			
		EditorPlatform.getEntityData().setComponent(EditorPlatform.getSelectionProperty().getValue().getEntityId(), newComp);
	}
	
	public void addComponent(String componentName){
		try {
			EntityComponent comp = EditorPlatform.getUserComponentList().getValue().get(componentName).newInstance();
			EditorPlatform.getEntityData().setComponent(EditorPlatform.getSelectionProperty().getValue().getEntityId(), comp);
		} catch (InstantiationException | IllegalAccessException e) {
			LogUtil.warning("Can't instanciate component "+componentName);
		}
	}
	
	public void removeComponent(Class<? extends EntityComponent> componentClass){
		EntityComponent comp = null;
		for(EntityComponent c : EditorPlatform.getSelectionProperty().getValue().componentListProperty())
			if(c.getClass() == componentClass){
				comp = c;
				break;
			}
		if(comp == null)
			throw new IllegalArgumentException("trying to remove a component ("+componentClass.getSimpleName()+") that doesn't exist on current entity");
		EditorPlatform.getSelectionProperty().getValue().componentListProperty().remove(comp);
		EditorPlatform.getEntityData().removeComponent(EditorPlatform.getSelectionProperty().getValue().getEntityId(), componentClass);
	}
	
	public List<String> getComponentNames(){
		return EditorPlatform.getUserComponentList().getValue().getSortedNames();
	}

}
