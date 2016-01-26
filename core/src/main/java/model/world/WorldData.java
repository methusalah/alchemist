package model.world;

import java.util.ArrayList;
import java.util.List;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import app.AppFacade;
import controller.regionPaging.RegionPager;
import model.ES.component.Naming;
import model.ES.component.motion.PlanarStance;
import model.ES.serial.EntityInstance;
import model.world.terrain.heightmap.HeightMapNode;
import util.geometry.geom2d.Point2D;
import view.drawingProcessors.TerrainDrawer;

public class WorldData {
	private final EntityData ed;
	private final EntityId worldEntity;
	
	public EntityId getWorldEntity() {
		return worldEntity;
	}

	private Region lastRegion;

	public Region getLastRegion() {
		return lastRegion;
	}
	
	public WorldData(EntityData ed) {
		this.ed = ed;
		worldEntity = ed.createEntity();
		ed.setComponent(worldEntity, new Naming("World"));
	}
	
	public void setCoord(Point2D coord){
		List<Point2D> neededRegions = new ArrayList<Point2D>();
		int r = Region.RESOLUTION;
		for(int x = -r; x <= r; x += r)
			for(int y = -r; y <= r; y += r)
				neededRegions.add(coord.getAddition(x, y));

		AppFacade.getStateManager().getState(RegionPager.class).setNeededRegions(neededRegions);
	}
	
	public TerrainDrawer getTerrainDrawer(Region region){
		return AppFacade.getStateManager().getState(RegionPager.class).drawers.get(region);
	}
	
	public List<Region> getRegionsAtOnce(Point2D coord){
		return AppFacade.getStateManager().getState(RegionPager.class).getRegionsAtOnce(coord);
	}
	
	/**
	 * find the height at given coordinate, plus the heights in neighboring regions if coordinate
	 * is above west or south border.
	 * @param coord
	 * @return
	 */
	public List<HeightMapNode> getHeights(Point2D coord){
		List<HeightMapNode> res = new ArrayList<>();
		for(Region r : getRegionsAtOnce(coord))
			res.add(r.getTerrain().getHeightMap().get(coord));
		return res;
	}

	public List<HeightMapNode> get4HeightsAround(Point2D coord){
		List<HeightMapNode> res = new ArrayList<>();
		res.addAll(getHeights(coord.getAddition(1, 0)));
		res.addAll(getHeights(coord.getAddition(-1, 0)));
		res.addAll(getHeights(coord.getAddition(0, 1)));
		res.addAll(getHeights(coord.getAddition(0, -1)));
		return res;
	}
	
	public void addEntityInstance(EntityInstance ei){
		for(EntityComponent comp : ei.getComps())
			if(comp instanceof PlanarStance){
				Point2D coord = ((PlanarStance)comp).getCoord();
				getRegionsAtOnce(coord).get(0).getEntities().add(ei);
			}
		ei.instanciate(ed, worldEntity);
	}
	
	public void saveDrawnRegions(){
		for(Region r : AppFacade.getStateManager().getState(RegionPager.class).builtRegions)
			RegionLoader.saveRegion(r);
	}
}
