package model;

import java.util.ArrayList;
import java.util.List;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableObjectValue;
import util.LogUtil;

public class EntityPresenter {
	List<ChangeListener<? super EntityPresenter>> changeListeners = new ArrayList<>();

	private final EntityId entityId;
	private final StringProperty name = new SimpleStringProperty();
	private final ListProperty<EntityComponent> componentList = new SimpleListProperty<>();
	private final ListProperty<EntityPresenter> childrenList = new SimpleListProperty<>();
	
	public EntityPresenter(EntityId id, String name) {
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

	public ListProperty<EntityPresenter> childrenListProperty(){
		return childrenList;
	}
}
