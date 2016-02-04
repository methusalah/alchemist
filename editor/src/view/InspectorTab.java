package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.simsilica.es.EntityComponent;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import presenter.InspectorPresenter;
import presenter.common.EntityNode;
import util.LogUtil;
import view.controls.ComponentEditor;

public class InspectorTab extends Tab {
	final InspectorPresenter presenter;
	VBox compControl;
	Label info;
	Button addCompButton;
	
	Map<Class<? extends EntityComponent>, ComponentEditor> editors = new HashMap<>();
	
	public InspectorTab() {
		presenter = new InspectorPresenter(this);
		
		setText("Inspector");
		setClosable(false);
		
		ScrollPane scroller = new ScrollPane();
		
		VBox content = new VBox();
		content.prefWidthProperty().bind(scroller.widthProperty());
		info = new Label("");
		content.getChildren().add(info);
		
		compControl = new VBox();
		content.getChildren().add(compControl);
		
		addCompButton = new Button("Add component");
		addCompButton.setVisible(false);
		addCompButton.setOnAction(e -> showComponentChooser());
		content.getChildren().add(addCompButton);
		scroller.setContent(content);
		scroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		setContent(scroller);
	}
	
	public void inspectNewEntity(EntityNode ep){
		compControl.getChildren().clear();
		editors.clear();
		if(ep == null){
			info.textProperty().unbind();
			info.setText("No entity selected");
			return;
		}
		for(EntityComponent comp : ep.componentListProperty()){
			ComponentEditor editor = new ComponentEditor(presenter, comp);
			editors.put(comp.getClass(), editor);
			compControl.getChildren().add(editor);
		}
		info.textProperty().bind(ep.nameProperty());
		addCompButton.setVisible(true);
	}
	
	public void updateComponentEditor(EntityComponent comp){
		ComponentEditor editor = editors.get(comp.getClass());
		if(editor.isExpanded())
			editor.updateComponent(comp);
	}
	
	public void addComponentEditor(EntityComponent comp){
		ComponentEditor editor = new ComponentEditor(presenter, comp);
		editors.put(comp.getClass(), editor);
		compControl.getChildren().add(editor);
	}
	
	public void removeComponentEditor(EntityComponent comp){
		ComponentEditor editor = editors.get(comp.getClass());
		editors.remove(comp.getClass());
		compControl.getChildren().remove(editor);
	}
	
	public void showComponentChooser(){
		ChoiceDialog<String> dialog = new ChoiceDialog<>(presenter.getComponentNames().get(0), presenter.getComponentNames());
		dialog.setTitle("Component choice".toUpperCase());
		dialog.setHeaderText(null);
		dialog.setContentText("Choose the component in the list :");
		Optional<String> result = dialog.showAndWait();
		result.ifPresent(compName -> presenter.addComponent(compName));
	}
}
