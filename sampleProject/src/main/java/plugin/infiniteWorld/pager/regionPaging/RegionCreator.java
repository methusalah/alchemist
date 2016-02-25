package plugin.infiniteWorld.pager.regionPaging;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import model.ECS.builtInComponent.Naming;
import model.ECS.builtInComponent.Parenting;
import plugin.infiniteWorld.pager.builder.Builder;
import plugin.infiniteWorld.pager.builder.BuilderReference;
import plugin.infiniteWorld.rendering.TerrainDrawer;
import plugin.infiniteWorld.world.EntityInstance;
import plugin.infiniteWorld.world.Region;
import plugin.infiniteWorld.world.RegionId;
import plugin.infiniteWorld.world.RegionLoader;
import plugin.infiniteWorld.world.terrain.heightmap.HeightMapNode;
import plugin.infiniteWorld.world.terrain.heightmap.Parcel;
import util.geometry.geom2d.Point2D;
import util.geometry.geom2d.Segment2D;
import util.geometry.geom3d.Point3D;
import util.geometry.geom3d.Segment3D;
import util.geometry.geom3d.Triangle3D;
import util.math.Fraction;

import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import component.motion.PlanarStance;
import component.motion.physic.EdgedCollisionShape;
import component.motion.physic.Physic;

public class RegionCreator implements BuilderReference {

	private final RegionId id;
	private final RegionLoader loader;
	private final EntityData ed;
	private final EntityId worldEntity;
	private final Consumer2<Region, TerrainDrawer> applyHandler;
	private final Consumer<Region> releaseHandler;
	
	private Region region;
	private TerrainDrawer drawer;
	private boolean applied = false;
	
	@FunctionalInterface
	interface Consumer2 <A, B> {
		public void accept(A a, B b);
	}
	
	public RegionCreator(RegionId id, RegionLoader loader, EntityData ed, EntityId worldEntity, Consumer2<Region, TerrainDrawer> resultHandler, Consumer<Region> releaseHandler) {
		this.id = id;
		this.loader = loader;
		this.ed = ed;
		this.worldEntity = worldEntity;
		this.applyHandler = resultHandler;
		this.releaseHandler = releaseHandler;
	}
	
	@Override
	public void build(){
		region = loader.getRegion(id);
		// create an entity for this region
		region.setEntityId(ed.createEntity());
		ed.setComponent(region.getEntityId(), new Naming("Region "+region.getId()));
		ed.setComponent(region.getEntityId(), new Parenting(worldEntity));
		
		// instantiation of entity blueprints
		for(EntityInstance ei : region.getEntities())
			ei.instanciate(ed, region.getEntityId());
		
		// creation of collision entities for terrain
		for(Parcel p : region.getTerrain().getParcelling().getAll()){
			List<Segment2D> edges = new ArrayList<>();
			for(HeightMapNode node : p.getHeights())
				for(Triangle3D t : p.getTriangles().get(node)){
					if(t.a.z == 0 && t.b.z == 0 && t.c.z == 0)
						continue;
					Segment3D border = getPlaneIntersection(t);
					if(border != null && border.p0.getDistance(border.p1) > 0){
						edges.add(new Segment2D(border.p0.get2D(), border.p1.get2D()));
					}
				}
			if(!edges.isEmpty()){
				EntityId pe = ed.createEntity();
				ed.setComponent(pe, new Parenting(region.getEntityId()));
				ed.setComponent(pe, new Naming("Parcel collision shape "+region.getTerrain().getParcelling().getCoord(p.getIndex())));
				ed.setComponent(pe, new EdgedCollisionShape(edges));
				ed.setComponent(pe, new Physic(Point2D.ORIGIN, "terrain", new ArrayList<>(), 1000000, new Fraction(0.2), null));
				ed.setComponent(pe, new PlanarStance());
				region.getTerrainColliders().add(pe);
			}
		}

		// creation of a terrain drawer
		drawer = new TerrainDrawer(region.getTerrain(), region.getCoord());
		drawer.render();
	}

	private static Point3D getPlaneIntersection(Segment3D seg){
		Point3D planeNormal = Point3D.UNIT_Z; 
		Point3D direction = seg.p1.getSubtraction(seg.p0);
		double a = -planeNormal.getDotProduct(seg.p0);
		double b = planeNormal.getDotProduct(direction);
		if(b == 0)
			return null;
		double r = a/b;
		if(r < 0 || r > 1)
			return null;
		
		return seg.p0.getAddition(direction.getMult(r));
	}
	
	private static Segment3D getPlaneIntersection(Triangle3D t){
		List<Point3D> intersections = new ArrayList<Point3D>();
		for(Segment3D s : t.getEdges()){
			Point3D i = getPlaneIntersection(s);
			if(i != null)
				intersections.add(i);
		}
		
		if(intersections.size() == 2)
			return new Segment3D(intersections.get(0), intersections.get(1));
		else
			return null;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public void apply(Builder builder) {
		applyHandler.accept(region, drawer);
		applied = true;
	}

	@Override
	public void release(Builder builder) {
		for(EntityInstance ei : region.getEntities())
			ei.uninstanciate(ed);
		for(EntityId eid : region.getTerrainColliders())
			ed.removeEntity(eid);
		ed.removeEntity(region.getEntityId());
		region.getTerrainColliders().clear();
		if(applied)
			releaseHandler.accept(region);
	}

	public Region getRegion() {
		return region;
	}
	
	
}
