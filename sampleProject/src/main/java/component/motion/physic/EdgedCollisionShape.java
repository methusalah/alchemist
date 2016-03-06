package component.motion.physic;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

import util.geometry.geom2d.Segment2D;

public class EdgedCollisionShape implements EntityComponent {
	private final List<Segment2D> edges;
	private final double restitution;
	
	public EdgedCollisionShape() {
		edges = new ArrayList<>();
		restitution = 0;
	}

	public EdgedCollisionShape(@JsonProperty("edges")List<Segment2D> edges,
			@JsonProperty("restitution")double restitution) {
		this.edges = edges;
		this.restitution = restitution;
	}

	public List<Segment2D> getEdges() {
		return edges;
	}

	public double getRestitution() {
		return restitution;
	}

}
