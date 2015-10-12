package model;

import java.util.ArrayList;
import java.util.List;

import com.simsilica.es.EntityId;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableObjectValue;
import util.LogUtil;

public class EntityNode implements ObservableObjectValue<EntityNode>{
	List<ChangeListener<? super EntityNode>> changeListeners = new ArrayList<>();

	public EntityId parent;
	private String name; 
	public List<EntityNode> children = new ArrayList<>();
	
	public EntityNode(EntityId parent, String name) {
		this.parent = parent;
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
		for(ChangeListener<? super EntityNode> l : changeListeners)
			l.changed(this, null, this);
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public void addListener(ChangeListener<? super EntityNode> arg0) {
		changeListeners.add(arg0);
	}

	@Override
	public EntityNode getValue() {
		return this;
	}

	@Override
	public void removeListener(ChangeListener<? super EntityNode> arg0) {
		changeListeners.remove(arg0);
	}

	@Override
	public void addListener(InvalidationListener arg0) {
	}

	@Override
	public void removeListener(InvalidationListener arg0) {
	}

	@Override
	public EntityNode get() {
		return this;
	}
}
