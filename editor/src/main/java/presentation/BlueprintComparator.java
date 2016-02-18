package main.java.presentation;

import java.util.Comparator;

import main.java.model.tempImport.Blueprint;


public class BlueprintComparator implements Comparator<Blueprint> {

	@Override
	public int compare(Blueprint bp1, Blueprint bp2) {
		return bp1.getName().compareToIgnoreCase(bp2.getName());
	}

}
