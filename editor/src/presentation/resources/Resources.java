package presentation.resources;

import java.util.ArrayList;
import java.util.List;

import com.simsilica.es.EntityComponent;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import model.ES.component.Parenting;
import model.ES.serial.Blueprint;
import model.ES.serial.BlueprintLibrary;
import presentation.resources.customControl.BlueprintListView;
import presentation.util.ViewLoader;
import presenter.common.EntityNode;
import presenter.util.BlueprintComparator;


public class Resources extends BorderPane {
	
	private ListProperty<Blueprint> blueprintList;
	
    public Resources() {
    	ViewLoader.loadFXMLForControl(this);
    }
    
	@FXML
	private void initialize() {
		blueprintList = new SimpleListProperty<Blueprint>(FXCollections.observableArrayList());
		for(Blueprint bp : BlueprintLibrary.getAllBlueprints())
			blueprintList.add(bp);
		blueprintList.sort(new BlueprintComparator());

		BlueprintListView bpListView = new BlueprintListView();
		bpListView.itemsProperty().bind(blueprintList);
		bpListView.setOnSaveEntity(e -> saveEntity(e));
		setCenter(bpListView);
		setAlignment(bpListView, Pos.CENTER);
	}
	
	public void saveEntity(EntityNode ep){
		Blueprint saved = getBlueprintFromPresenter(ep);
		BlueprintLibrary.saveBlueprint(saved);
		
		for(Blueprint bp : blueprintList)
			if(bp.getName().equalsIgnoreCase(saved.getName())){
				blueprintList.set(blueprintList.indexOf(bp), saved);
				return;
			}
		// at this point, the saved blueprint doesn't replace any existing blueprint
		blueprintList.add(saved);
		blueprintList.sort(new BlueprintComparator());
	}
	
	private Blueprint getBlueprintFromPresenter(EntityNode ep){
		// we have to ignore the parenting component, for it is created from the blueprint tree
		List<EntityComponent> comps = new ArrayList<>();
		for(EntityComponent comp : ep.componentListProperty())
			if(!(comp instanceof Parenting))
				comps.add(comp);

		List<Blueprint> children = new ArrayList<>();
		for(EntityNode child : ep.childrenListProperty())
			children.add(getBlueprintFromPresenter(child));
		
		return new Blueprint(ep.nameProperty().getValue(),
				comps,
				children);
	}
}
