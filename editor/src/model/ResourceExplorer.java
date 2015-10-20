package model;

import java.util.ArrayList;
import java.util.List;

import com.simsilica.es.EntityId;

import model.ES.serial.Blueprint;
import model.ES.serial.BlueprintLibrary;

public class ResourceExplorer {

	public ResourceExplorer() {
		
	}
	
	public List<String> getBlueprintNames(){
		List<String> res = new ArrayList<>();
		for(Blueprint bp : BlueprintLibrary.getAll()){
			res.add(bp.name);
		}
		return res;
	}
	
	public void saveEntity(EntityId eid, Inspector inspector){
//		Blueprint bp = new Blueprint(name, inspector.getScannableComponents(eid), children)
//		BlueprintLibrary.save(bp);
	}
	
	
}

