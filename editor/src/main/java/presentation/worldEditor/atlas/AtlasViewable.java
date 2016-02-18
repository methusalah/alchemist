package main.java.presentation.worldEditor.atlas;

import java.util.Optional;

import main.java.model.world.terrain.TerrainTexture;

public interface AtlasViewable {
	public void updateTextureGrid();
	public Optional<TerrainTexture> showTerrainTextureDialog(TerrainTexture terrainTexture);
}
