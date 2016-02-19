package model.world.terrain;

import java.util.ArrayList;

public class TerrainTexturing extends ArrayList<TerrainTexture> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int TEXTURE_COUNT = 8; 
	
	public TerrainTexturing() {
	}
	
	public TerrainTexturing(TerrainTexture... textures) {
		int index = 0;
		for(TerrainTexture tex : textures){
			add(tex);
			index++;
		}
		for(int i = index; i < TEXTURE_COUNT; i++)
			add(null);
	}
	
	@Override
	public boolean add(TerrainTexture e) {
		if(size() >= TEXTURE_COUNT)
			throw new RuntimeException("Impossible to add more than 8 textures.");
		return super.add(e);
	}
	
	@Override
	public void add(int index, TerrainTexture element) {
		if(size() >= TEXTURE_COUNT || index >= TEXTURE_COUNT)
			throw new RuntimeException("Impossible to add more than 8 textures.");
		super.add(index, element);
	}
}
