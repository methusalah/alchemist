package model.ES.component.motion.physic;

import java.util.ArrayList;
import java.util.List;

import util.geometry.geom2d.Segment2D;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class EdgedCollisionShape implements EntityComponent {
	private final List<Segment2D> edges;
	
	public EdgedCollisionShape() {
		edges = new ArrayList<>();
	}

	public EdgedCollisionShape(@JsonProperty("edges")List<Segment2D> edges) {
		this.edges = edges;
	}

	public List<Segment2D> getEdges() {
		return edges;
	}

}
