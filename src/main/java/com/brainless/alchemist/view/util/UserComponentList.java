package com.brainless.alchemist.view.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.simsilica.es.EntityComponent;

public class UserComponentList extends HashMap<String, Class<? extends EntityComponent>>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserComponentList() {
		Reflections reflections = new Reflections();    
		Set<Class<? extends EntityComponent>> components = reflections.getSubTypesOf(EntityComponent.class);
		
		for (Class<? extends EntityComponent> component: components) {
			add(component);
		}
	}
	
	@SafeVarargs
	protected final void add(Class<? extends EntityComponent> ... compClasses){
		for(Class<? extends EntityComponent> compClass : compClasses)
			put(compClass.getSimpleName(), compClass);
	}

	public List<String> getSortedNames(){
		List<String> res = new ArrayList<String>(keySet());
		Collections.sort(res);
		return res;
	}
}
