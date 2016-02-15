package presentation.worldEditor.presenter.atlas;

import java.util.Optional;

import model.world.terrain.TerrainTexture;

public interface AtlasViewable {
	public void updateTextureGrid();
	public Optional<TerrainTexture> showTerrainTextureDialog(TerrainTexture terrainTexture);
}
