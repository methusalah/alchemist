package com.brainless.alchemist.presentation.common;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ToggledEnumProperty<T extends Enum<T>> extends SimpleObjectProperty<T>{
	
	private Map<T, BooleanProperty> toggles = new HashMap<>();

	public ToggledEnumProperty(Class<T> c) {
		for(T value : c.getEnumConstants()){
			BooleanProperty toggle = new SimpleBooleanProperty();
			toggle.addListener((observable, oldValue, newValue) -> {
				if(newValue && !oldValue)
					select(value);
			});
			toggles.put(value, toggle);
		}
		addListener((observable, oldValue, newValue) -> {
			if(newValue != oldValue)
				select(newValue);
		});
	}
	
	private void select(T value){
		BooleanProperty toggleToSelect = toggles.get(value);
		for(BooleanProperty toggle : toggles.values())
			if(toggle != toggleToSelect)
				toggle.setValue(false);
		setValue(value);
	}
	
	public BooleanProperty getToggle(T value){
		return toggles.get(value);
	}
}
