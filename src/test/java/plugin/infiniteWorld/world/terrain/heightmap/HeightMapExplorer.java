package plugin.infiniteWorld.world.terrain.heightmap;

import java.util.HashMap;
import java.util.Map;

public class HeightMapExplorer {

	private final Map<HeightMap, Parcelling> parcellings = new HashMap<>();
	
	public void add(HeightMap heightMap){
		parcellings.put(heightMap, new Parcelling(heightMap));
	}
	
	
	
}
