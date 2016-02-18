package model.ES.processor.world;

import java.util.ArrayList;
import java.util.List;

import com.jme3.app.state.AppStateManager;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

import app.AppFacade;
import controller.regionPaging.RegionPager;
import main.java.model.ECS.pipeline.Processor;
import model.ECS.Naming;
import model.ES.component.motion.ChasingCamera;
import model.ES.component.motion.PlanarStance;
import model.ES.serial.EntityInstance;
import model.tempImport.DataState;
import model.world.Region;
import model.world.RegionLoader;
import model.world.terrain.heightmap.HeightMapNode;
import util.geometry.geom2d.Point2D;
import view.drawingProcessors.TerrainDrawer;

public class WorldProc extends Processor {
	
	private EntityId worldEntity;

	private Point2D oldCoord = null;
	private Region lastRegion;

	public Region getLastRegion() {
		return lastRegion;
	}

	@Override
	protected void onInitialized(AppStateManager stateManager) {
		worldEntity = stateManager.getState(DataState.class).getEntityData().createEntity();
		entityData.setComponent(worldEntity, new Naming("World"));
		setCoord(Point2D.ORIGIN);
	}

	@Override
	protected void registerSets() {
		registerDefault(ChasingCamera.class, PlanarStance.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		onEntityUpdated(e);
	}
	
	@Override
	protected void onEntityUpdated(Entity e) {
		PlanarStance stance = e.get(PlanarStance.class);
		if(oldCoord == null || oldCoord.getDistance(stance.getCoord()) > 5){
			oldCoord = stance.getCoord();
			setCoord(stance.getCoord());
		}
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
		ei.instanciate(entityData, worldEntity);
	}
	
	public void saveDrawnRegions(){
		for(Region r : AppFacade.getStateManager().getState(RegionPager.class).builtRegions)
			RegionLoader.saveRegion(r);
	}

	public EntityId getWorldEntity() {
		return worldEntity;
	}
	
	

	
	

}
