package util.geometry.structure.grid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class GridNode {
	
	@JsonProperty
	protected final int index;

	public GridNode(int index) {
		this.index = index;
	}

	@JsonIgnore
	public int getIndex() {
		return index;
	}
}
