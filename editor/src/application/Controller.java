package application;

import com.google.common.eventbus.Subscribe;
import com.jme3.cinematic.TimeLine;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import model.Model;
import util.LogUtil;
import util.event.AddComponentEvent;
import util.event.ComponentPropertyChanged;
import util.event.EntityCreationEvent;
import util.event.EntityCreationFromBlueprintEvent;
import util.event.EntityDeletionEvent;
import util.event.EntityRenamedEvent;
import util.event.EntitySelectionChanged;
import util.event.EventManager;
import util.event.ParentingChangedEvent;
import util.event.RemoveComponentEvent;
import util.event.SaveEntityEvent;
import view.Overview;

public class Controller {

	private final Model model;
	private final Overview view;
	
	public Controller(Model model, Overview view) {
		this.model = model;
		this.view = view;
		
		view.hierarchyView.setRootPresenter(model.hierarchy.getRootEntityPresenter());
		view.inspectorView.setComponentNames(model.inspector.getComponentNames());
		view.resourceView.setBlueprintList(model.resourceExplorer.blueprintListProperty());
		EventManager.register(this);
	}
	
	@Subscribe
	public void handleEntitySelectionChangedEvent(EntitySelectionChanged e){
		model.selectionProperty.setValue(e.ep);
	}
	
	@Subscribe
	public void handleUpdateComponentEvent(ComponentPropertyChanged e){
		model.inspector.updateComponent(e.comp, e.propertyName, e.newValue);
	}
	
	@Subscribe
	public void handleEntityCreationEvent(EntityCreationEvent e){
		model.hierarchy.createNewEntity();
	}

	@Subscribe
	public void handleEntityCreationFromBlueprintEvent(EntityCreationFromBlueprintEvent e){
		model.hierarchy.createNewEntityFromBlueprint(e.getBp());
	}
	
	@Subscribe
	public void handleAddComponentEvent(AddComponentEvent e){
		model.inspector.addComponent(e.compName);
	}

	@Subscribe
	public void handleRemoveComponentEvent(RemoveComponentEvent e){
		model.inspector.removeComponent(e.compClass);
	}
	
	@Subscribe
	public void handleParentingChangedEvent(ParentingChangedEvent e){
		model.hierarchy.updateParenting(e.child, e.newParent);
	}

	@Subscribe
	public void handleEntityDeletionEvent(EntityDeletionEvent e){
		model.hierarchy.removeEntity(e.getEp());
	}
	
	@Subscribe
	public void handleSaveEntityEvent(SaveEntityEvent e){
		model.resourceExplorer.saveEntity(e.ep);
	}
}
