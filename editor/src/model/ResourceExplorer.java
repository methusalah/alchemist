package model;

import java.util.ArrayList;
import java.util.List;

import model.ES.serial.Blueprint;
import model.ES.serial.BlueprintLibrary;

public class ResourceExplorer {

	public ResourceExplorer() {
		
	}
	
	public List<String> getBlueprintsName(){
		List<String> res = new ArrayList<>();
		for(Blueprint bp : BlueprintLibrary.getAll()){
			res.add(bp.name);
		}
		return res;
	}
	
	
}

