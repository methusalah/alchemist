package presentation.util;

import java.util.Comparator;

import model.ES.serial.Blueprint;

public class BlueprintComparator implements Comparator<Blueprint> {

	@Override
	public int compare(Blueprint bp1, Blueprint bp2) {
		return bp1.getName().compareToIgnoreCase(bp2.getName());
	}

}
