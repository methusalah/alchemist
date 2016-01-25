package controller.regionPaging;

import java.util.function.Consumer;

import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import controller.builder.Builder;
import controller.builder.BuilderReference;
import model.ES.serial.EntityInstance;
import model.world.Region;
import model.world.RegionLoader;
import util.geometry.geom2d.Point2D;

public class RegionDestructor implements BuilderReference {

	private final Point2D coord;
	private final RegionLoader loader;
	private final EntityData ed;
	private final Consumer<Region> resultHandler;
	
	private Region region;
	
	public RegionDestructor(Point2D coord, RegionLoader loader, EntityData ed, Consumer<Region> resultHandler) {
		this.coord = coord;
		this.loader = loader;
		this.ed = ed;
		this.resultHandler = resultHandler;
	}
	
	@Override
	public void build(){
		region = loader.getRegion(coord);
		for(EntityInstance ei : region.getEntities())
			ei.uninstanciate(ed);
		for(EntityId eid : region.getTerrainColliders())
			ed.removeEntity(eid);
		region.getTerrainColliders().clear();
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public void apply(Builder builder) {
		resultHandler.accept(region);
	}

	@Override
	public void release(Builder builder) {
		// TODO Auto-generated method stub
		
	}

}
