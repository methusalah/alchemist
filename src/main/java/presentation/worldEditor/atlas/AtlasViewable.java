package presentation.worldEditor.atlas;

import java.util.Optional;

public interface AtlasViewable {
	public void updateTextureGrid();
	public Optional<TerrainTexture> showTerrainTextureDialog(TerrainTexture terrainTexture);
}
