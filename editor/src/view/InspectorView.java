package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.simsilica.es.EntityComponent;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.ES.component.Naming;
import view.controls.ComponentEditor;

public class InspectorView extends VBox{
	VBox compControl;
	Label title;
	Label info;
	
	Map<Class<? extends EntityComponent>, ComponentEditor> editors = new HashMap<>();
	
	public InspectorView() {
		setPrefWidth(400);
		title = new Label("Inspector");
		title.setMinHeight(40);
		title.setMaxWidth(Double.MAX_VALUE);
		title.setStyle("-fx-background-color: lightblue");
		getChildren().add(title);
		
		info = new Label("");
		getChildren().add(info);
		
		compControl = new VBox();
		getChildren().add(compControl);
	}
	
	public void inspectNewEntity(List<EntityComponent> comps){
		info.setText("");
		compControl.getChildren().clear();
		editors.clear();
		for(EntityComponent comp : comps){
			if(comp instanceof Naming)
				info.setText("Name : "+((Naming)comp).name);
			ComponentEditor editor = new ComponentEditor(comp);
			editors.put(comp.getClass(), editor);
			compControl.getChildren().add(editor);
		}
	}
	
	public void inspectSameEntity(List<EntityComponent> comps){
		info.setText("");
		for(EntityComponent comp : comps){
			if(comp instanceof Naming)
				info.setText("Name : "+((Naming)comp).name);
			ComponentEditor editor = editors.get(comp.getClass());
			if(editor != null)
				editor.updateComponent(comp);
		}
	}

	public void updateEntityName(String name){
		info.setText("Name : "+name);
		
	}
	
}
