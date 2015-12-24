package model.world;

import java.util.ArrayList;
import java.util.List;

import model.ES.component.Naming;
import model.ES.component.hierarchy.Parenting;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.physic.EdgedCollisionShape;
import model.ES.component.motion.physic.Physic;
import model.ES.serial.EntityInstance;
import model.world.terrain.heightmap.HeightMapNode;
import model.world.terrain.heightmap.Parcel;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.geometry.geom2d.Segment2D;
import util.geometry.geom3d.Point3D;
import util.geometry.geom3d.Segment3D;
import util.geometry.geom3d.Triangle3D;
import util.math.Fraction;

import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

public class RegionArtisan {

	
	public static void drawRegion(EntityData ed, Region region){
		LogUtil.info("draw region "+region.getId()+" ("+RegionArtisan.class+")");
		// instantiation of entity blueprints
		for(EntityInstance ei : region.getEntities())
			ei.instanciate(ed, region.getEntityId());
		
		// Drawing of the terrain
		region.getDrawer().render();
		
		// creation of collision entities for terrain
		region.getTerrainColliders().clear();
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
	}
	
	public static void undrawRegion(EntityData ed, EntityId worldEid, Region region){
		LogUtil.info("Undraw region "+region.getId()+" ("+RegionArtisan.class+")");
		for(EntityInstance ei : region.getEntities())
			ei.uninstanciate(ed);
		for(EntityId eid : region.getTerrainColliders())
			ed.removeEntity(eid);
		region.getDrawer().toDetach = true;
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

}
