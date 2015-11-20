package model.world.terrain.heightmap;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jme3tools.navigation.Coordinate;
import model.world.terrain.Terrain;
import util.LogUtil;
import util.exception.TechnicalException;
import util.geometry.collections.Map2D;
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

	private static final int PARCEL_SIZE = 10;
	
	private final HeightMap heightMap;

	public Parcelling(HeightMap heightmap) {
		super((int) Math.ceil((double) heightmap.xSize() / PARCEL_SIZE),
				(int) Math.ceil((double) heightmap.ySize() / PARCEL_SIZE),
				heightmap.getCoord());
		
		this.heightMap = heightmap;
		int nbParcel = xSize*ySize;
		for (int i = 0; i < nbParcel; i++) {
			set(i, new Parcel(this, i));
		}

		for (int x = 0; x < heightmap.xSize(); x++) {
			for (int y = 0; y < heightmap.ySize(); y++) {
				Point2D pInSpace = new Point2D(x, y).getAddition(coord);
				get(inParcellingSpace(pInSpace)).add(heightmap.get(pInSpace));
			}
		}

		for (Parcel p : getAll()) {
			compute(p);
		}
	}

	private static int inParcellingSpace(double valInTerrainSpace){
		return (int) (valInTerrainSpace / PARCEL_SIZE);
	}

	private Point2D inParcellingSpace(Point2D pInTerrainSpace){
		return new Point2D(inParcellingSpace(pInTerrainSpace.x-coord.x)+coord.x, inParcellingSpace(pInTerrainSpace.y-coord.y)+coord.y);
	}

	public List<Parcel> getParcelsContaining(List<Height> heights) {
		List<Parcel> res = new ArrayList<>();
		for (Height h : heights) {
			Parcel container = get(inParcellingSpace(heightMap.getCoord(h.getIndex())));
			if (!res.contains(container)) {
				res.add(container);
			}
		}
		return res;
	}

	public List<Parcel> updateParcelsContaining(List<Height> heights) {
		List<Parcel> res = getParcelsContaining(heights);
		for (Parcel p : res) {
			p.reset();
		}
		for (Parcel p : res) {
			compute(p);
		}
		return res;
	}

	private List<Triangle3D> getGroundTriangles(Height height, Parcel parcel) {
		if (heightMap.getEastNode(height) == null || heightMap.getNorthNode(height) == null) {
			return new ArrayList<>();
		}

		if (parcel.getTriangles().containsKey(height)) {
			if (parcel.getTriangles().get(height).isEmpty()) {
				parcel.getTriangles().get(height).addAll(getTerrainNodeGround(height));
			}
			return parcel.getTriangles().get(height);
		}
		for (Parcel neighbor : get8Around(parcel)) {
			for (Height h : neighbor.getTriangles().keySet()) {
				if (h.equals(height)) {
					return getGroundTriangles(h, neighbor);
				}
			}
		}
		throw new TechnicalException("Ground Triangle was not found, this must not happen. node : "+heightMap.getCoord(height.getIndex()));
	}

	private List<Triangle3D> getTerrainNodeGround(Height height) {
		Point3D sw = heightMap.getPos(height);
		Point3D se = heightMap.getPos(heightMap.getEastNode(height));
		Point3D nw = heightMap.getPos(heightMap.getNorthNode(height));
		Point3D ne = heightMap.getPos(heightMap.getNorthNode(heightMap.getEastNode(height)));

		List<Triangle3D> triangles = new ArrayList<>();
		triangles.add(new Triangle3D(sw, se, ne));
		triangles.add(new Triangle3D(sw, ne, nw));
		return triangles;
	}

	private List<Triangle3D> getNearbyTriangles(Height height, Parcel parcel) {
		List<Triangle3D> res = new ArrayList<>();
		for (Height n : heightMap.get9Around(height)) {
			res.addAll(getGroundTriangles(n, parcel));
		}
		return res;
	}

	private void compute(Parcel parcel) {
		double xScale = 1.0 / heightMap.xSize();
		double yScale = 1.0 / heightMap.ySize();
		for (Height height : parcel.getHeights()) {
			for (Triangle3D t : getGroundTriangles(height, parcel)) {
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

				for (Triangle3D n : getNearbyTriangles(height, parcel)) {
					List<Point3D> shared = t.getCommonPoints(n);
//					if (t.normal.getAngleWith(n.normal) > AngleUtil.RIGHT) {
//						continue;
//					}
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
