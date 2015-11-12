package model.world.terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import util.exception.TechnicalException;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.geometry.geom3d.Triangle3D;
import util.geometry.structure.grid.Grid;
import util.math.AngleUtil;

/**
 * Divides the node based grid into parcels for performance purpose. the objectives : - group nodes for the graphic card to manage less objects, - divide terrain to
 * prevent the graphic car to draw it entirely at each frame. Other resolutions may offer better results. Resolution may become dynamic. The challenge here is
 * to smooth texture at parcels' frontiers (see ParcelMesh)
 */
public class Parcelling extends Grid<Parcel>{

	private static final Logger logger = Logger.getLogger(Parcelling.class.getName());

	private static int RESOLUTION = 10;

	public Parcelling(Terrain terrain) {
		super((int) Math.ceil((double) terrain.xSize() / RESOLUTION), (int) Math.ceil((double) terrain.ySize() / RESOLUTION));
		int nbParcel = xSize*ySize;
		for (int i = 0; i < nbParcel; i++) {
			set(i, new Parcel(this, i));
		}

		for (int x = 0; x < terrain.xSize(); x++) {
			for (int y = 0; y < terrain.ySize(); y++) {
				get(inParcellingSpace(new Point2D(x, y))).add(terrain.get(x, y));
			}
		}

		for (Parcel p : getAll()) {
			compute(terrain, p);
		}
	}

	private static int inParcellingSpace(double valInTerrainSpace){
		return (int) (valInTerrainSpace / RESOLUTION);
	}

	private static Point2D inParcellingSpace(Point2D pInTerrainSpace){
		return new Point2D(inParcellingSpace(pInTerrainSpace.x), inParcellingSpace(pInTerrainSpace.y));
	}

	public List<Parcel> getParcelsContaining(List<TerrainNode> nodes) {
		List<Parcel> res = new ArrayList<>();
		for (TerrainNode n : nodes) {
			Parcel container = get(inParcellingSpace(n.getCoord()));
			if (!res.contains(container)) {
				res.add(container);
			}
		}
		return res;
	}

	public List<Parcel> updateParcelsContaining(List<TerrainNode> nodes) {
		Terrain m = nodes.get(0).getTerrain();
		List<Parcel> res = getParcelsContaining(nodes);
		for (Parcel p : res) {
			p.reset();
		}
		for (Parcel p : res) {
			compute(m, p);
		}
		return res;
	}

	private List<Triangle3D> getGroundTriangles(TerrainNode node, Parcel parcel) {
		if (node.e() == null || node.n() == null) {
			return new ArrayList<>();
		}

		if (parcel.triangles.containsKey(node)) {
			if (parcel.triangles.get(node).isEmpty()) {
				parcel.triangles.get(node).addAll(getTerrainNodeGround(node));
			}
			return parcel.triangles.get(node);
		}
		for (Parcel neighbor : get8Around(parcel)) {
			for (TerrainNode n : neighbor.triangles.keySet()) {
				if (n.equals(n)) {
					return getGroundTriangles(n, neighbor);
				}
			}
		}
		throw new TechnicalException("Ground Triangle was not found, this must not happen. node : "+node.getCoord());
	}

	private List<Triangle3D> getTerrainNodeGround(TerrainNode t) {
		Point3D sw = t.getPos();
		Point3D se = t.e().getPos();
		Point3D ne = t.e().n().getPos();
		Point3D nw = t.n().getPos();

		List<Triangle3D> triangles = new ArrayList<>();
		triangles.add(new Triangle3D(sw, se, ne));
		triangles.add(new Triangle3D(sw, ne, nw));
		return triangles;
	}

	public List<Triangle3D> getNearbyTriangles(TerrainNode t, Terrain terrain, Parcel parcel) {
		List<Triangle3D> res = new ArrayList<>();
		for (TerrainNode n : terrain.get9Around(t)) {
			res.addAll(getGroundTriangles(n, parcel));
		}
		return res;
	}

	public void compute(Terrain terrain, Parcel parcel) {
		double xScale = 1.0 / terrain.xSize();
		double yScale = 1.0 / terrain.ySize();
		for (TerrainNode node : parcel.getNodes()) {
			for (Triangle3D t : getGroundTriangles(node, parcel)) {
				int index = parcel.getMesh().vertices.size();
				parcel.getMesh().vertices.add(t.a);
				parcel.getMesh().vertices.add(t.b);
				parcel.getMesh().vertices.add(t.c);

				parcel.getMesh().indices.add(index);
				parcel.getMesh().indices.add(index + 1);
				parcel.getMesh().indices.add(index + 2);

				Point3D normal1 = t.normal;
				Point3D normal2 = t.normal;
				Point3D normal3 = t.normal;

				for (Triangle3D n : getNearbyTriangles(node, terrain, parcel)) {
					List<Point3D> shared = t.getCommonPoints(n);
					if (t.normal.getAngleWith(n.normal) > AngleUtil.RIGHT) {
						continue;
					}
					if (shared.size() == 3) {
						continue;
					}
					if (shared.contains(t.a)) {
						normal1 = normal1.getAddition(n.normal);
					}

					if (shared.contains(t.b)) {
						normal2 = normal2.getAddition(n.normal);
					}

					if (shared.contains(t.c)) {
						normal3 = normal3.getAddition(n.normal);
					}
				}

				if (normal1.isOrigin()) {
					parcel.getMesh().normals.add(t.normal);
				} else {
					parcel.getMesh().normals.add(normal1.getNormalized());
				}

				if (normal2.isOrigin()) {
					parcel.getMesh().normals.add(t.normal);
				} else {
					parcel.getMesh().normals.add(normal2.getNormalized());
				}

				if (normal3.isOrigin()) {
					parcel.getMesh().normals.add(t.normal);
				} else {
					parcel.getMesh().normals.add(normal3.getNormalized());
				}

				parcel.getMesh().textCoord.add(t.a.get2D().getMult(xScale, yScale));
				parcel.getMesh().textCoord.add(t.b.get2D().getMult(xScale, yScale));
				parcel.getMesh().textCoord.add(t.c.get2D().getMult(xScale, yScale));
			}
		}
	}

}
