package model.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import model.ES.serial.EntityInstance;
import model.world.terrain.heightmap.Height;
import model.world.terrain.heightmap.Parcelling;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import view.drawingProcessors.TerrainDrawer;

public class WorldData {
	private List<Region> drawnRegions = new ArrayList<Region>();
	private Map<Region, TerrainDrawer> terrainDrawers = new HashMap<>();
	private Region lastRegion;
	private RegionManager regionManager = new RegionManager();
	private final EntityData ed;
	private EntityId worldEntity;
	
	public WorldData(EntityData ed) {
		this.ed = ed;
	}
	
	public void setWorldEntity(EntityId eid){
		this.worldEntity = eid;
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
	
	public void drawRegion(Region region){
		LogUtil.info("draw region "+region.getId());
		for(EntityInstance ei : region.getEntities())
			ei.instanciate(ed, worldEntity);
		
		
		TerrainDrawer drawer = new TerrainDrawer(region.getTerrain(), region.getCoord());
		drawer.render();
		terrainDrawers.put(region, drawer);
	}
	
	private void undrawRegion(Region region){
		LogUtil.info("undraw region "+region.getId());
		for(EntityInstance ei : region.getEntities())
			ei.uninstanciate(ed);
	}
	
	private List<Region> get9RegionsAround(Point2D coord){
		List<Region> res = new ArrayList<Region>();
		int r = Region.RESOLUTION;
		for(int x = -r; x <= r; x += r)
			for(int y = -r; y <= r; y += r)
				res.add(regionManager.getRegion(coord.getAddition(x, y)));
		return res;
	}
	
	public List<Region> getRegions(Point2D coord){
		coord = new Point2D((int)coord.x, (int)coord.y);
		List<Region> res = new ArrayList<>();
		res.add(regionManager.getRegion(coord));
		if(coord.x % Region.RESOLUTION == 0)
			res.add(regionManager.getRegion(coord.getAddition(-1, 0)));
		if(coord.y % Region.RESOLUTION == 0)
			res.add(regionManager.getRegion(coord.getAddition(0, -1)));
		if(coord.x % Region.RESOLUTION == 0 && coord.y % Region.RESOLUTION == 0)
			res.add(regionManager.getRegion(coord.getAddition(-1, -1)));
		return res; 
	}
	
	public TerrainDrawer getTerrainDrawer(Region region) {
		return terrainDrawers.get(region);
	}
	
	/**
	 * find the height at given coordinate, plus the heights in neighboring regions if coordinate
	 * is above west or south border.
	 * @param coord
	 * @return
	 */
	public List<Height> getHeights(Point2D coord){
		List<Height> res = new ArrayList<>();
		for(Region r : getRegions(coord))
			res.add(r.getTerrain().getHeightMap().get(coord));
		return res;
	}
	
}
