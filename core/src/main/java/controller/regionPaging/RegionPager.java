package controller.regionPaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import app.AppFacade;
import controller.ECS.DataState;
import controller.builder.BuilderState;
import model.ES.component.Naming;
import model.ES.component.hierarchy.Parenting;
import model.world.Region;
import model.world.RegionArtisan;
import model.world.RegionLoader;
import model.world.WorldData;
import util.geometry.geom2d.Point2D;
import view.drawingProcessors.TerrainDrawer;

public class RegionPager extends BuilderState {
	
	private final RegionLoader loader = new RegionLoader();
	private EntityData entityData;
	private WorldData worldData;
	
	private final List<Point2D> neededRegions = new ArrayList<>();
	public final List<Region> builtRegions = new ArrayList<>();
	public final Map<Region, TerrainDrawer> drawers = new HashMap<>();
	
	private final Node worldNode;

	public RegionPager() {
		super("Region Builder", 20, 1);
		worldNode = new Node("World");
		AppFacade.getMainSceneNode().attachChild(worldNode);
	}
	
	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		worldData = stateManager.getState(DataState.class).getWorldData();
		entityData = stateManager.getState(DataState.class).getEntityData();
	}
	
	public void setNeededRegions(List<Point2D> newNeededRegions){
		// we start by transforming points in region Ids
		List<Point2D> stdPoints = new ArrayList<>();
		for(Point2D p : newNeededRegions)
			stdPoints.add(RegionLoader.getRegionCoord(p));
		
		// we have to find the built regions that are not needed anymore and discard them
		List<Point2D> discardedRegions = new ArrayList<>(neededRegions);
		discardedRegions.removeAll(stdPoints);
		for(Point2D coord : discardedRegions){
			getBuilder().build(new RegionDestructor(coord, loader, entityData, (region) -> {
				builtRegions.remove(region);
				worldNode.detachChild(drawers.get(region).mainNode);
				drawers.remove(region);
			}));
		}

		// and the new regions to build
		List<Point2D> missingRegions = new ArrayList<>(neededRegions);
		missingRegions.removeAll(neededRegions);
		for(Point2D coord : missingRegions){
			getBuilder().build(new RegionCreator(coord, loader, entityData, worldData.getWorldEntity(), (region, drawer) -> {
				builtRegions.add(region);
				drawers.put(region, drawer);
				worldNode.attachChild(drawer.mainNode);
			}));
		}

		neededRegions.clear();
		neededRegions.addAll(stdPoints);
	}
	
	public List<Region> getRegionsAtOnce(Point2D coord){
		coord = new Point2D((int)Math.floor(coord.x), (int)Math.floor(coord.y));
		List<Region> res = new ArrayList<>();
		res.add(loader.getRegion(coord));
		if(coord.x % Region.RESOLUTION == 0)
			res.add(loader.getRegion(coord.getAddition(-1, 0)));
		if(coord.y % Region.RESOLUTION == 0)
			res.add(loader.getRegion(coord.getAddition(0, -1)));
		if(coord.x % Region.RESOLUTION == 0 && coord.y % Region.RESOLUTION == 0)
			res.add(loader.getRegion(coord.getAddition(-1, -1)));
		for(Region r : res)
			if(!builtRegions.contains(r)){
				builtRegions.add(r);
				RegionCreator creator = new RegionCreator(coord, loader, entityData, worldData.getWorldEntity(), (region, drawer) -> {
					builtRegions.add(region);
					drawers.put(region, drawer);
					worldNode.attachChild(drawer.mainNode);
				});
				creator.build();
				creator.apply(getBuilder());
			}
		return res; 
	}

}
