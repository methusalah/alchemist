package view.tab.inspector;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.simsilica.es.EntityComponent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import presentation.common.EntityNode;
import presentation.inspector.InspectorPresenter;
import presentation.inspector.InspectorViewer;
import view.tab.inspector.customControl.ComponentEditor;
import view.util.ViewLoader;

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
