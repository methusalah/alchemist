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
	private final List<Region> builtRegions = new ArrayList<>();
	private final Map<Region, TerrainDrawer> drawers = new HashMap<>();
	
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
		List<Point2D> discardedRegions = new ArrayList<>(neededRegions);
		discardedRegions.removeAll(newNeededRegions);
		for(Point2D coord : discardedRegions){
			getBuilder().build(new RegionDestructor(coord, loader, entityData, (region) -> {
				builtRegions.remove(region);
				worldNode.detachChild(drawers.get(region).mainNode);
				drawers.remove(region);
			}));
		}

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
		neededRegions.addAll(newNeededRegions);
	}
}
