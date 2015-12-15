package model.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Line;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import app.AppFacade;
import model.ES.component.Naming;
import model.ES.component.hierarchy.Parenting;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.physic.EdgedCollisionShape;
import model.ES.component.motion.physic.Physic;
import model.ES.serial.EntityInstance;
import model.world.terrain.heightmap.HeightMapExplorer;
import model.world.terrain.heightmap.HeightMapNode;
import model.world.terrain.heightmap.Parcel;
import model.world.terrain.heightmap.Parcelling;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.geometry.geom2d.Segment2D;
import util.geometry.geom3d.Point3D;
import util.geometry.geom3d.Segment3D;
import util.geometry.geom3d.Triangle3D;
import util.math.Fraction;
import view.drawingProcessors.TerrainDrawer;
import view.material.MaterialManager;
import view.math.TranslateUtil;

public class WorldData {
	private final EntityData ed;
	private final EntityId worldEntity;

	private List<Region> drawnRegions = new ArrayList<Region>();
//	private Map<Region, TerrainDrawer> terrainDrawers = new HashMap<>();
	private Map<Region, List<EntityId>> terrainColliderLists = new HashMap<>();

	private HeightMapExplorer heightmapExplorer = new HeightMapExplorer();
	private Region lastRegion;
	private RegionManager regionManager = new RegionManager();
	
	public WorldData(EntityData ed) {
		this.ed = ed;
		worldEntity = ed.createEntity();
		ed.setComponent(worldEntity, new Naming("World"));
	}
	
	public void setCoord(Point2D coord){
		Region actualRegion = regionManager.getRegion(coord);
		
		if(actualRegion != lastRegion){
			// We pass from a region to another
			lastRegion = actualRegion;
			new Thread(new RegionLoader(coord, true)).start();;
		}
	}
	
	class RegionLoader implements Runnable{
		private final Point2D coord;
		
		public RegionLoader(Point2D coord, boolean toDraw) {
			this.coord = coord;
		}
		
		@Override
		public void run() {
			synchronized (drawnRegions) {
				List<Region> toDraw = get9RegionsAround(coord);
				for(Region r : toDraw)
					if(!drawnRegions.contains(r))
						drawRegion(r);
				
				for(Region r : drawnRegions)
					if(!toDraw.contains(r) && !r.isModified())
						undrawRegion(r);
				drawnRegions = toDraw;
			}
		}
	}
	
	public void attachDrawers(){
		synchronized (drawnRegions) {
			for(Region r : drawnRegions)
				if(!r.getDrawer().attached)
					AppFacade.getRootNode().attachChild(r.getDrawer().mainNode);
		}
	}

	public void drawRegion(Region region){
		LogUtil.info(region+"draw region "+region.getId());
		RegionArtisan.drawRegion(ed, worldEntity, region);
		heightmapExplorer.add(region.getTerrain().getHeightMap());
	}
	
	private void undrawRegion(Region region){
		LogUtil.info(this+"undraw region "+region.getId());
		RegionArtisan.undrawRegion(ed, worldEntity, region);
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
		coord = new Point2D((int)Math.floor(coord.x), (int)Math.floor(coord.y));
		List<Region> res = new ArrayList<>();
		res.add(regionManager.getRegion(coord));
		if(coord.x % Region.RESOLUTION == 0)
			res.add(regionManager.getRegion(coord.getAddition(-1, 0)));
		if(coord.y % Region.RESOLUTION == 0)
			res.add(regionManager.getRegion(coord.getAddition(0, -1)));
		if(coord.x % Region.RESOLUTION == 0 && coord.y % Region.RESOLUTION == 0)
			res.add(regionManager.getRegion(coord.getAddition(-1, -1)));
		for(Region r : res)
			if(!drawnRegions.contains(r))
				drawRegion(r);
		return res; 
	}
	
	/**
	 * find the height at given coordinate, plus the heights in neighboring regions if coordinate
	 * is above west or south border.
	 * @param coord
	 * @return
	 */
	public List<HeightMapNode> getHeights(Point2D coord){
		List<HeightMapNode> res = new ArrayList<>();
		for(Region r : getRegions(coord))
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
				getRegions(coord).get(0).getEntities().add(ei);
			}
		ei.instanciate(ed, worldEntity);
	}
	
	public void saveDrawnRegions(){
		for(Region r : drawnRegions)
			RegionManager.saveRegion(r);
	}
}
