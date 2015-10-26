package model.ES.serial;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import util.exception.TechnicalException;

public class BlueprintLibrary {
	private static String path = "assets/data/blueprint/";
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final Map<String, Blueprint> blueprintMap;

	static {
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		blueprintMap = new HashMap<>();
		loadBlueprints();
	}
	
	private BlueprintLibrary(){
		
	}
	
	public static void saveBlueprint(Blueprint bp){
		try {
			mapper.writeValue(new File(path + bp.getName()), bp);
			blueprintMap.put(bp.getName(), bp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void loadBlueprints(){
		for(File f : getFilesDeeply(path))
			try {
				Blueprint bp = mapper.readValue(f, Blueprint.class);
				blueprintMap.put(bp.getName(), bp);
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
	
	public static List<Blueprint> getAllBlueprints(){
		return new ArrayList<>(blueprintMap.values());
	}
	
	public static Blueprint getBlueprint(String name){
		return blueprintMap.get(name);
	}
}
