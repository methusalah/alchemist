package application;

import com.google.common.eventbus.Subscribe;

import model.Model;
import util.event.ComponentPropertyChanged;
import util.event.EntityCreationEvent;
import util.event.EntityRenamedEvent;
import util.event.EntitySelectionChanged;
import util.event.EventManager;
import view.View;

public class Controller {

	private final Model model;
	private final View view;
	
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		
		view.hierarchyView.update(model.hierarchy.baseNodes);
		EventManager.register(this);
	}
	
	@Subscribe
	public void EntitySelectionChangedEvent(EntitySelectionChanged event){
		model.inspector.inspect(event.eid);
		view.inspectorView.inspectNewEntity(model.inspector.getComponents());
	}
	
	@Subscribe
	public void updateComponentEvent(ComponentPropertyChanged event){
		model.inspector.updateComponent(event.comp, event.propertyName, event.newValue);
		view.inspectorView.inspectSameEntity(model.inspector.getComponents());
	}
	
	@Subscribe
	public void entityCreationEvent(EntityCreationEvent event){
		model.hierarchy.createNewEntity("Unamed Entity");
		view.hierarchyView.update(model.hierarchy.baseNodes);
	}
	
	@Subscribe
	public void entityRenamedEvent(EntityRenamedEvent event){
		// wathever the source of the modification, we change the naming of the entity
		model.inspector.updateName(event.eid, event.newName);
		
		// then we update the views
		model.hierarchy.updateName(event.eid);
		view.inspectorView.updateEntityName(event.newName);
		
		
	}
	

}
