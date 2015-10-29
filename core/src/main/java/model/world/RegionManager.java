package model.world;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import util.geometry.geom2d.Point2D;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class RegionManager {
	private static final String PATH = "assets/regions/";
	private static final String EXT = ".region";
	
	private ObjectMapper mapper = new ObjectMapper();
	Map<String, Region> loadedRegions = new HashMap<>();

	public RegionManager() {
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	public Region getRegion(Point2D coord){
		String rid = getRegionId(coord);
		if(!loadedRegions.containsKey(rid))
			loadedRegions.put(rid, loadRegion(rid));
		return loadedRegions.get(rid);
	}
	
	private Region loadRegion(String rid) {
		File f = getRegionFile(rid);
		
		try {
			return mapper.readValue(f, Region.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getRegionId(Point2D coord){
		int x = (int)Math.floor(coord.x/Region.RESOLUTION);
		int y = (int)Math.floor(coord.y/Region.RESOLUTION);
		return x+","+y;
	}
	
	public void saveRegion(Region region){
		File f = getRegionFile(region.id);
		try {
			mapper.writeValue(f, region);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private File getRegionFile(String regionId){
		File f = new File(PATH+regionId+EXT);
		if (!f.exists()) {
			try {
				f.createNewFile();
				mapper.writeValue(f, new Region(regionId));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f;
	}
}
