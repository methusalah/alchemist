package presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import model.ECS.event.ComponentSetEvent;
import model.ES.component.Naming;
import model.ES.component.hierarchy.Parenting;
import util.LogUtil;
import util.event.EventManager;

public class EntityNode {
	List<ChangeListener<? super EntityNode>> changeListeners = new ArrayList<>();

	private final EntityId entityId;
	private final StringProperty name = new SimpleStringProperty();
	private final ListProperty<EntityComponent> componentList = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final ListProperty<EntityNode> childrenList = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	public EntityNode(EntityId id, String name) {
		this.entityId = id;
		this.name.setValue(name);
	}

	@Override
	public String toString() {
		return name.getValue();
	}

	public EntityId getEntityId() {
		return entityId;
	}
	
	public StringProperty nameProperty(){
		return name;
	}
	
	public ListProperty<EntityComponent> componentListProperty(){
		return componentList;
	}

	public ListProperty<EntityNode> childrenListProperty(){
		return childrenList;
	}
}
