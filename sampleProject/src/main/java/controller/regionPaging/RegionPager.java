package controller.regionPaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jme3.scene.Node;

import app.AppFacade;
import controller.ECS.DataState;
import controller.builder.BuilderState;
import model.ES.processor.world.WorldProc;
import model.world.Region;
import model.world.RegionId;
import model.world.RegionLoader;
import util.geometry.geom2d.Point2D;
import view.drawingProcessors.TerrainDrawer;

public class RegionPager extends BuilderState {
	
	private final RegionLoader loader = new RegionLoader();
	
	private final List<RegionId> neededRegions = new ArrayList<>();
	private final Map<RegionId, RegionCreator> creators = new HashMap<>();
	public final List<Region> builtRegions = new ArrayList<>();
	public final Map<Region, TerrainDrawer> drawers = new HashMap<>();
	
	private final Node worldNode;

	public RegionPager() {
		super("Region Builder", 1, 1);
		worldNode = new Node("World");
		AppFacade.getMainSceneNode().attachChild(worldNode);
	}
	
	public void setNeededRegions(List<Point2D> newNeededRegions){

		// we start by transforming points in region Ids
		List<RegionId> ids = new ArrayList<>();
		for(Point2D p : newNeededRegions)
			ids.add(new RegionId(p));
		
		List<RegionId> discardedRegions = new ArrayList<>(neededRegions);
		List<RegionId> missingRegions = new ArrayList<>(ids);

		// we have to find the built regions that are not needed anymore and discard them
		discardedRegions.removeAll(ids);
		for(RegionId id : discardedRegions){
			if(creators.containsKey(id)){
				// TODO what happen when you discared a non ready region?
				if(creators.get(id).getRegion().isModified())
					// 	we have to keep modified regions as needed, since they have to stay drawn while they are not saved
					ids.add(id);
				else{
					getBuilder().release(creators.get(id));
					creators.remove(id);
				}
			}
		}

		// and the new regions to build
		missingRegions.removeAll(neededRegions);
		for(RegionId id : missingRegions){
			RegionCreator creator = instanciateCreator(id);
			if(!creators.containsKey(id)){
				creators.put(id, creator);
				getBuilder().build(creator);
			}
		}
		neededRegions.clear();
		neededRegions.addAll(ids);
	}
	
	/**
	 * Meant to be called from another thread, to obtain regions immediately
	 * 
	 * @param coord
	 * @return null if one or more required region is not built
	 */
	public List<Region> getRegionsAtOnce(Point2D coord){
		// on a single point, we can have up to four regions.
		// this happens on the borders and corners
		coord = new Point2D((int)Math.floor(coord.x), (int)Math.floor(coord.y));
		List<RegionId> ids = new ArrayList<>();
		ids.add(new RegionId(coord));
		if(coord.x % Region.RESOLUTION == 0)
			ids.add(new RegionId(coord.getAddition(-1, 0)));
		if(coord.y % Region.RESOLUTION == 0)
			ids.add(new RegionId(coord.getAddition(0, -1)));
		if(coord.x % Region.RESOLUTION == 0 && coord.y % Region.RESOLUTION == 0)
			ids.add(new RegionId(coord.getAddition(-1, -1)));

		// for each region ID, we get the existing regions or ask the Builder to build it
		// since this method is made to obtain built regions, we wait for it to finish the job
		List<Region> res = new ArrayList<>();
		for(RegionId id : ids){
			if(!creators.containsKey(id)){
				RegionCreator creator = instanciateCreator(id);
				synchronized (this) {
					creators.put(id, creator);
					neededRegions.add(id);
					getBuilder().build(creator);
				}
				while(!builtRegions.contains(creator.getRegion()))
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
			res.add(loader.getRegion(id));
		}
		return res; 
	}
	
	private RegionCreator instanciateCreator(RegionId id){
		return new RegionCreator(id,
				loader,
				AppFacade.getStateManager().getState(DataState.class).getEntityData(),
				AppFacade.getStateManager().getState(WorldProc.class).getWorldEntity(),
				(region, drawer) -> {
					builtRegions.add(region);
					drawers.put(region, drawer);
					worldNode.attachChild(drawer.mainNode);
				},
				(region) -> {
					builtRegions.remove(region);
					worldNode.detachChild(drawers.get(region).mainNode);
					drawers.remove(region);
				}
				);
	}
}
