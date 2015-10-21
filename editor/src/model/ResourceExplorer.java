package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import util.LogUtil;
import util.exception.TechnicalException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ResourceExplorer {
	private static String path = "assets/data/blueprint/";
	private final ObjectMapper mapper = new ObjectMapper();
	private final Map<String, Blueprint> blueprintMap;
	private final ListProperty<Blueprint> blueprintList;

	public ResourceExplorer() {
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		blueprintMap = new HashMap<>();
		blueprintList = new SimpleListProperty<Blueprint>(FXCollections.observableArrayList());
		loadBlueprints();
	}
	
	public ListProperty<Blueprint> blueprintListProperty(){
		return blueprintList;
	}
	
	public void saveEntity(EntityPresenter ep){
		Blueprint bp = new Blueprint(ep);
		try {
			mapper.writeValue(new File(path + bp.getName()), bp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		addBlueprint(bp);
	}
	
	private void loadBlueprints(){
		for(File f : getFilesDeeply(path))
			try {
				Blueprint bp = mapper.readValue(f, Blueprint.class);
				addBlueprint(bp); 
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
	
	private void addBlueprint(Blueprint bp){
		blueprintMap.put(bp.getName(), bp);
		blueprintList.add(bp);
	}

	
	
}

