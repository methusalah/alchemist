package model.world.terrain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import util.geometry.geom3d.MyMesh;
import util.geometry.geom3d.Triangle3D;
import util.geometry.structure.grid.Node;

public class Parcel extends Node {

	Map<TerrainNode, List<Triangle3D>> triangles = new HashMap<TerrainNode, List<Triangle3D>>();

	private MyMesh mesh = new MyMesh();
	
	public Parcel(Parcelling grid, int index) {
		super(grid, index);
	}

	public void add(TerrainNode t) {
		triangles.put(t, new ArrayList<Triangle3D>());
	}

	public List<TerrainNode> getNodes() {
		List<TerrainNode> res = new ArrayList<>();
		for (TerrainNode t : triangles.keySet()) {
			res.add(t);
		}
		return res;
	}

	public void reset() {
		mesh.vertices.clear();
		mesh.textCoord.clear();
		mesh.normals.clear();
		mesh.indices.clear();
		for (TerrainNode t : triangles.keySet()) {
			triangles.get(t).clear();
		}
	}

	public MyMesh getMesh() {
		return mesh;
	}

	public void setMesh(MyMesh mesh) {
		this.mesh = mesh;
	}
}
