package presentation.base;

import java.util.Comparator;

import model.ECS.blueprint.Blueprint;


public class BlueprintComparator implements Comparator<Blueprint> {

	@Override
	public int compare(Blueprint bp1, Blueprint bp2) {
		return bp1.getName().compareToIgnoreCase(bp2.getName());
	}

}
