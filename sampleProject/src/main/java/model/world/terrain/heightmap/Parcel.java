package model.world.terrain.heightmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import util.geometry.geom3d.MyMesh;
import util.geometry.geom3d.Triangle3D;
import util.geometry.structure.grid.GridNode;

public class Parcel extends GridNode {

	private final Map<HeightMapNode, List<Triangle3D>> triangles = new HashMap<HeightMapNode, List<Triangle3D>>();
	private MyMesh mesh = new MyMesh();
	
	public Parcel(Parcelling grid, int index) {
		super(index);
	}

	public void add(HeightMapNode h) {
		triangles.put(h, new ArrayList<Triangle3D>());
	}

	public List<HeightMapNode> getHeights() {
		List<HeightMapNode> res = new ArrayList<>();
		for (HeightMapNode t : triangles.keySet()) {
			res.add(t);
		}
		return res;
	}

	public void reset() {
		mesh.vertices.clear();
		mesh.textCoord.clear();
		mesh.normals.clear();
		mesh.indices.clear();
		for (HeightMapNode t : triangles.keySet()) {
			triangles.get(t).clear();
		}
	}

	public MyMesh getMesh() {
		return mesh;
	}

	public void setMesh(MyMesh mesh) {
		this.mesh = mesh;
	}

	public Map<HeightMapNode, List<Triangle3D>> getTriangles() {
		return triangles;
	}
}
