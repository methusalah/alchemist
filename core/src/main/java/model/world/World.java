package model.world;

import java.util.ArrayList;
import java.util.List;

import com.simsilica.es.EntityData;

import model.ES.serial.EntityInstance;
import model.ES.serial.EntityInstancier;
import util.LogUtil;
import util.geometry.geom2d.Point2D;

public class World {
	private List<Region> drawnRegions = new ArrayList<Region>();
	private Region lastRegion;
	private RegionManager regionManager = new RegionManager();
	private final EntityData ed;
	
	public World(EntityData ed) {
		this.ed = ed;
	}
	
	public void setCoord(Point2D coord){
		Region actualRegion = regionManager.getRegion(coord);
		
		if(actualRegion != lastRegion){
			// We pass from a region to another
			lastRegion = actualRegion;
			List<Region> toDraw = get9RegionsAround(coord);
			for(Region r : toDraw)
				if(!drawnRegions.contains(r))
					drawRegion(r);
			
			for(Region r : drawnRegions)
				if(!toDraw.contains(r))
					undrawRegion(r);
			
			drawnRegions = toDraw;
		}
	}
	
	private void drawRegion(Region region){
		LogUtil.info("draw region "+region.getId());
		for(EntityInstance ei : region.getEntities())
			EntityInstancier.instanciate(ei);
	}
	
	private void undrawRegion(Region region){
		LogUtil.info("undraw region "+region.getId());
		
	}
	
	private List<Region> get9RegionsAround(Point2D coord){
		List<Region> res = new ArrayList<Region>();
		int r = Region.RESOLUTION;
		for(int x = -r; x <= r; x += r)
			for(int y = -r; y <= r; y += r)
				res.add(regionManager.getRegion(coord.getAddition(x, y)));
		return res;
	}
}
