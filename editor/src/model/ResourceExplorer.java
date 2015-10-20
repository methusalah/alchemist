package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.simsilica.es.EntityId;

import model.ES.serial.EntityPrototype;
import util.exception.TechnicalException;
import model.ES.serial.BlueprintLibrary;

public class ResourceExplorer {
	private static String path = "assets/data/blueprint/";
	private static ObjectMapper mapper = new ObjectMapper();
	private static Map<String, Blueprint> blueprints = new HashMap<>();

	public ResourceExplorer() {
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		loadBlueprints();
	}
	
	public List<String> getBlueprintNames(){
		return new ArrayList<>(blueprints.keySet());
	}
	
	public static void saveEntity(EntityPresenter ep){
		Blueprint bp = new Blueprint(ep);
		try {
			mapper.writeValue(new File(path + bp.getName()), bp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		blueprints.put(bp.getName(), bp);
	}
	
	private void loadBlueprints(){
		for(File f : getFilesDeeply(path))
			try {
				Blueprint bp = mapper.readValue(f, Blueprint.class);
				blueprints.put(bp.getName(), bp); 
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	private static ArrayList<File> getFilesDeeply(String folderPath) {
		ArrayList<File> res = new ArrayList<>();
		File folder = new File(folderPath);
		if (!folder.exists()) {
			throw new TechnicalException("the folder " + folderPath +  " was not found.");
		}
		for (File f : folder.listFiles()) {
			if(f.isDirectory())
				res.addAll(getFilesDeeply(f.getPath()));
			else
				res.add(f);
		}
		return res;
	}

	
	
}

