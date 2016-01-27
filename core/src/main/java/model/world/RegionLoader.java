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

public class RegionLoader {
	private static final String PATH = "assets/data/regions/";
	private static final String EXT = ".region";
	
	private final ObjectMapper mapper = new ObjectMapper();
	private final Map<RegionId, Region> loadedRegions = new HashMap<>();
	private final List<Region> cache = new ArrayList<>();

	public RegionLoader() {
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}

	public Region getRegion(Point2D coord){
		RegionId id = new RegionId(coord);
		Region res;
		synchronized (loadedRegions) {
			if(!loadedRegions.containsKey(id))
				loadedRegions.put(id, loadRegion(id, getRegionCoord(coord)));
			res = loadedRegions.get(id);
			
			synchronized (cache) {
				// cleaning the cache
				if(cache.contains(res))
					cache.remove(res);
				cache.add(0, res);
				
				int tryCount = 0;
				if(cache.size() > 20)
					while(cache.size() > 15 && tryCount++ < 50){
						// TODO refactor with region Id in regions
						Region oldest = new ArrayList<Region>(loadedRegions.values()).get(cache.size()-1);
						if(!oldest.isModified()){
							loadedRegions.remove(oldest.getId());
							cache.remove(oldest);
						}
					}
			}
		}
		return res;
	}
	
	private Region loadRegion(RegionId id, Point2D coord) {
		File f = new File(PATH+id.getId()+EXT);
		if (!f.exists()) {
			return new Region(id.getId(), id.getOffset());
		} else{
			try {
				return mapper.readValue(f, Region.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
			LogUtil.warning("problem with region loading for " + id);
			return null;
		}
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
}
