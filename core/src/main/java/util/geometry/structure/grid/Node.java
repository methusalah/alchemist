package util.geometry.structure.grid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Node {
	
	@JsonProperty
	protected final int index;

	public Node(int index) {
		this.index = index;
	}

	@JsonIgnore
	public int getIndex() {
		return index;
	}
}
