package plugin.infiniteWorld.editor.presentation.atlas;

import java.util.Optional;

import plugin.infiniteWorld.world.terrain.TerrainTexture;

public interface AtlasConfiguratorViewer {
	public void updateTextureGrid();
	public Optional<TerrainTexture> showTerrainTextureDialog(TerrainTexture terrainTexture);
}
