package com.brainless.alchemist.presentation.base;

import java.util.Comparator;

import com.brainless.alchemist.model.ECS.blueprint.Blueprint;


public class BlueprintComparator implements Comparator<Blueprint> {

	@Override
	public int compare(Blueprint bp1, Blueprint bp2) {
		return bp1.getName().compareToIgnoreCase(bp2.getName());
	}

}
