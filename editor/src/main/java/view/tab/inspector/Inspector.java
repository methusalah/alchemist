package main.java.view.tab.inspector;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

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
import main.java.model.EditorPlatform;
import main.java.presentation.EntityNode;
import main.java.presentation.inspector.InspectorPresenter;
import main.java.presentation.inspector.InspectorViewer;
import main.java.view.tab.inspector.customControl.ComponentEditor;
import main.java.view.util.ViewLoader;
import util.LogUtil;

public class Inspector extends BorderPane implements InspectorViewer {

	private final InspectorPresenter presenter;
	
	private final Map<Class<? extends EntityComponent>, ComponentEditor> editors = new HashMap<>();

	@FXML
	VBox componentBox;
	
	@FXML
	Label infoLabel;
	
	@FXML
	Button addButton;

	public Inspector() {
		presenter = new InspectorPresenter(this);
		ViewLoader.loadFXMLForControl(this);
	}
	
	@FXML
	private void initialize(){
		addButton.setOnAction(e -> showComponentChooser());
		inspectNewEntity(null);
	}
	
	@Override
	public void inspectNewEntity(EntityNode ep){
		componentBox.getChildren().clear();
		editors.clear();
		infoLabel.textProperty().unbind();
		
		addButton.visibleProperty().setValue(ep != null);
		
		if(ep == null){
			infoLabel.setText("No entity selected");
		} else {
			for(EntityComponent comp : ep.componentListProperty())
				addComponentEditor(comp);
			infoLabel.textProperty().bind(ep.nameProperty());
		}
	}
	
	@Override
	public void updateComponentEditor(EntityComponent comp){
		ComponentEditor editor = editors.get(comp.getClass());
		if(editor.isExpanded())
			editor.updateComponent(comp);
	}
	
	@Override
	public void addComponentEditor(EntityComponent comp){
		ComponentEditor editor = new ComponentEditor(comp,
				compClass -> presenter.removeComponent(compClass),
				(c, propertyName, value) -> presenter.updateComponent(c, propertyName, value));
		editors.put(comp.getClass(), editor);
		componentBox.getChildren().add(editor);
	}
	
	@Override
	public void removeComponentEditor(EntityComponent comp){
		ComponentEditor editor = editors.get(comp.getClass());
		editors.remove(comp.getClass());
		componentBox.getChildren().remove(editor);
	}
	
	private void showComponentChooser(){
		ChoiceDialog<String> dialog = new ChoiceDialog<>(presenter.getComponentNames().get(0), presenter.getComponentNames());
		dialog.setTitle("Component choice".toUpperCase());
		dialog.setHeaderText(null);
		dialog.setContentText("Choose the component in the list :");
		Optional<String> result = dialog.showAndWait();
		result.ifPresent(compName -> presenter.addComponent(compName));
	}
}
