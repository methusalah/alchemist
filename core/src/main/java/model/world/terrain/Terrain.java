package model.world.terrain;

import model.world.terrain.atlas.Atlas;
import util.geometry.structure.grid3D.Grid3D;

public final class Terrain extends Grid3D<TerrainNode> {
	
	private final Parcelling parcelling;
	private final Atlas atlas, cover;
	
	public Terrain(int width, int height) {
		super(width, height);
		atlas = new Atlas(width, height);
		cover = new Atlas(width, height);
		parcelling = new Parcelling(this); 
	}
	
	public Atlas getAtlas() {
		return atlas;
	}

	public Atlas getCover() {
		return cover;
	}

	public Parcelling getParcelling() {
		return parcelling;
	}
	
}
