package model.world;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import util.LogUtil;
import util.geometry.geom2d.Point2D;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class RegionManager {
	private static final String PATH = "assets/data/regions/";
	private static final String EXT = ".region";
	
	private ObjectMapper mapper = new ObjectMapper();
	Map<String, Region> loadedRegions = new HashMap<>();

	public RegionManager() {
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	public Region getRegion(Point2D coord){
		String rid = getRegionId(coord);
		LogUtil.info(this + "region need : "+rid);
		if(!loadedRegions.containsKey(rid))
			loadedRegions.put(rid, loadRegion(rid));
		return loadedRegions.get(rid);
	}
	
	private Region loadRegion(String rid) {
		LogUtil.info(this + "    region load : "+rid);
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
	
	public static void saveRegion(Region region){
		File f = getRegionFile(region.getId());
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			mapper.writeValue(f, region);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static File getRegionFile(String regionId){
		File f = new File(PATH+regionId+EXT);
		if (!f.exists()) {
			try {
				f.createNewFile();
				ObjectMapper mapper = new ObjectMapper();
				mapper.enable(SerializationFeature.INDENT_OUTPUT);
				mapper.writeValue(f, new Region(regionId));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f;
	}
}
