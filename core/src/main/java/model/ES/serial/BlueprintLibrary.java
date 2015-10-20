package model.ES.serial;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.LogUtil;
import util.exception.TechnicalException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class BlueprintLibrary {
	private static String path = "assets/data/blueprint/";
	private static ObjectMapper mapper = new ObjectMapper();
	private static Map<String, EntityPrototype> blueprints = new HashMap<>();

	static {
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		for(File f : getFilesDeeply(path))
			try {
				EntityPrototype bp = mapper.readValue(f, EntityPrototype.class);
				blueprints.put(bp.name, bp); 
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	
	private BlueprintLibrary(){
	}
	
	public static void save(EntityPrototype bp){
		try {
			mapper.writeValue(new File(path+bp.name), bp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		blueprints.put(bp.name, bp);
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
	
	public static EntityPrototype get(String name){
		return blueprints.get(name);
	}
	
	public static List<EntityPrototype> getAll(){
		return new ArrayList<EntityPrototype>(blueprints.values());
	}
}
