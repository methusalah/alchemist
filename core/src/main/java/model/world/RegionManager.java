package model.world;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.LogUtil;
import util.geometry.geom2d.Point2D;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class RegionManager {
	private static final String PATH = "assets/data/regions/";
	private static final String EXT = ".region";
	
	private final ObjectMapper mapper = new ObjectMapper();
	private final Map<String, Region> loadedRegions = new HashMap<>();
	private final List<Region> cache = new ArrayList<>();

	public RegionManager() {
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}

	public Region getRegion(Point2D coord){
		String rid = getRegionId(coord);
		if(!loadedRegions.containsKey(rid))
			loadedRegions.put(rid, loadRegion(rid, getRegionCoord(coord)));
		Region res = loadedRegions.get(rid);
		
		// cleaning the cache
		if(cache.contains(res))
			cache.remove(res);
		cache.add(0, res);
		
		if(cache.size() > 20)
			while(cache.size() > 15){
				loadedRegions.remove(cache.get(cache.size()-1).getId());
				cache.remove(cache.size()-1);
			}
		return res;
	}
	
	private Region loadRegion(String rid, Point2D coord) {
		LogUtil.info("    load region file : "+rid+" ("+RegionManager.class+")");
		File f = getRegionFile(rid, coord);
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

	public static Point2D getRegionCoord(Point2D coord){
		int x = ((int)Math.floor(coord.x/Region.RESOLUTION))*Region.RESOLUTION;
		int y = ((int)Math.floor(coord.y/Region.RESOLUTION))*Region.RESOLUTION;
		return new Point2D(x, y);
	}

	public static void saveRegion(Region region){
		File f = new File(PATH+region.getId()+EXT);
		if (!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
			mapper.writeValue(f, region);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static File getRegionFile(String regionId, Point2D coord){
		File f = new File(PATH+regionId+EXT);
		if (!f.exists()) {
			try {
				f.createNewFile();
				ObjectMapper mapper = new ObjectMapper();
				mapper.enable(SerializationFeature.INDENT_OUTPUT);
				mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
				mapper.writeValue(f, new Region(regionId, coord));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f;
	}
}
