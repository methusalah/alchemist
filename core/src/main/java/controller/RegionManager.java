package controller;

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
import model.ES.component.Naming;
import model.ES.component.hierarchy.Parenting;
import model.world.Region;
import model.world.RegionArtisan;
import model.world.RegionLoader;
import model.world.WorldData;
import util.geometry.geom2d.Point2D;
import view.drawingProcessors.TerrainDrawer;

public class RegionManager extends AbstractAppState {
	
	private final RegionLoader loader = new RegionLoader();
	private EntityData entityData;
	private WorldData worldData;
	
	private final List<Point2D> toLoad = new ArrayList<>();
	
	private final List<Point2D> toDraw = new ArrayList<>();
	private final List<Region> drawn = new ArrayList<>();
	
	private Map<Region, TerrainDrawer> drawers = new HashMap<>();
	private final List<TerrainDrawer> drawersToAttach = new ArrayList<TerrainDrawer>();
	private final List<TerrainDrawer> drawersToDetach = new ArrayList<TerrainDrawer>();
	private final Node worldNode;

	public RegionManager() {
		worldNode = new Node("World");
		AppFacade.getMainSceneNode().attachChild(worldNode);
	}
	
	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		worldData = stateManager.getState(DataState.class).getWorldData();
		entityData = stateManager.getState(DataState.class).getEntityData();
	}

	@Override
	public void update(float tpf) {
		loadRegions();
		drawRegions();
		attachDrawers();
		detachDrawers();
	}
	
	private void loadRegions(){
		for(Point2D coord : toLoad)
			RegionLoader.loadRegion(coord);
	}
	
	private void drawRegions(){
		for(Point2D coord : toLoad){
			Region r = RegionLoader.getLoadedRegion(coord);
			if(r == null)
				toLoad.add(coord);
			else{
				// create an entity for this region
				if(r.getEntityId() == null){
					EntityId eid = entityData.createEntity();
					entityData.setComponent(eid, new Naming("Region "+r.getId()));
					//entityData.setComponent(eid, new Parenting(worldEntity));
					r.setEntityId(eid);
				}
				
				if(!drawn.contains(r)){
					drawn.add(r);
					drawRegion(r);
				}
			}
		}
		
//		for(Region r : new ArrayList<>(drawn))
//			if(!toDraw.contains(r) && !r.isModified()){
//				drawn.remove(r);
//				undrawRegion(r);
//			}
	}
	
	private void attachDrawers(){
		synchronized (drawersToAttach) {
			for(TerrainDrawer d : drawersToAttach){
				worldNode.attachChild(d.mainNode);
			}
			drawersToAttach.clear();
		}
	}
	
	private void detachDrawers(){
		synchronized (drawersToDetach) {
			for(TerrainDrawer d : drawersToDetach){
				worldNode.detachChild(d.mainNode);
			}
			drawersToDetach.clear();
		}
	}
	
	private void drawRegion(Region region){
		if(!drawers.containsKey(region))
			drawers.put(region, new TerrainDrawer(region.getTerrain(), region.getCoord()));
		RegionArtisan.drawRegion(entityData, region);
		drawers.get(region).render();
		synchronized (drawersToAttach) {
			drawersToAttach.add(drawers.get(region));
		}
	}
	
	private void undrawRegion(Region region){
		RegionArtisan.undrawRegion(entityData, region);
		synchronized (drawersToDetach) {
			drawersToDetach.add(drawers.get(region));
		}
	}

	
}
