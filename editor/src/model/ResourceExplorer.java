package model;

import java.util.ArrayList;
import java.util.List;

import com.simsilica.es.EntityComponent;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import model.ES.component.hierarchy.Parenting;
import model.ES.serial.Blueprint;
import model.ES.serial.BlueprintLibrary;

public class ResourceExplorer {
	private final ListProperty<Blueprint> blueprintList;

	public ResourceExplorer() {
		blueprintList = new SimpleListProperty<Blueprint>(FXCollections.observableArrayList());
		for(Blueprint bp : BlueprintLibrary.getAllBlueprints())
			blueprintList.add(bp);
	}
	
	public ListProperty<Blueprint> blueprintListProperty(){
		return blueprintList;
	}
	
	public void saveEntity(EntityPresenter ep){
		Blueprint saved = getBlueprintFromPresenter(ep);
		BlueprintLibrary.saveBlueprint(saved);
		blueprintList.add(saved);
	}
	
	private Blueprint getBlueprintFromPresenter(EntityPresenter ep){
		// we have to ignore the parenting component, for it is created from the blueprint tree
		List<EntityComponent> comps = new ArrayList<>();
		for(EntityComponent comp : ep.componentListProperty())
			if(!(comp instanceof Parenting))
				comps.add(comp);

		List<Blueprint> children = new ArrayList<>();
		for(EntityPresenter child : ep.childrenListProperty())
			children.add(getBlueprintFromPresenter(child));
		
		return new Blueprint(ep.nameProperty().getValue(),
				comps,
				children);
	}
}

