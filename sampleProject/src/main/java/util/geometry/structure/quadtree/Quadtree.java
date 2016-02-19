package util.geometry.structure.quadtree;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import util.geometry.geom2d.AlignedBoundingBox;
import util.geometry.geom2d.BoundingShape;
import util.geometry.geom2d.Point2D;
import util.geometry.geom2d.Rectangle;

public class Quadtree {
	private final static int MAX_OBJECTS = 10;
	private final static int MAX_LEVELS = 5;
	private int level;
	private List<Object> objects;
	private AlignedBoundingBox bounds;
	private Quadtree[] nodes;

	/*
	 * Constructor
	 */
	public Quadtree(int pLevel, AlignedBoundingBox pBounds) {
		level = pLevel;
		objects = new ArrayList<>();
		bounds = pBounds;
		nodes = new Quadtree[4];
	}

	/*
	 * Clears the quadtree
	 */
	public void clear() {
		objects.clear();

		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i] != null) {
				nodes[i].clear();
				nodes[i] = null;
			}
		}
	}

	/*
	 * Splits the node into 4 subnodes
	 */
	private void split() {
		int subWidth = (int) (bounds.getWidth() / 2);
		int subHeight = (int) (bounds.getHeight() / 2);
		Point2D corner = bounds.getLocation();
		nodes[0] = new Quadtree(level + 1, new AlignedBoundingBox(corner.getAddition(subWidth, 0), subWidth, subHeight));
		nodes[1] = new Quadtree(level + 1, new AlignedBoundingBox(corner, subWidth, subHeight));
		nodes[2] = new Quadtree(level + 1, new AlignedBoundingBox(corner.getAddition(0, subHeight), subWidth, subHeight));
		nodes[3] = new Quadtree(level + 1, new AlignedBoundingBox(corner.getAddition(subWidth, subHeight), subWidth, subHeight));
	}

	/*
	 * Determine which node the object belongs to. -1 means object cannot
	 * completely fit within a child node and is part of the parent node
	 */
	private int getIndex(BoundingShape shape) {
		int index = -1;
		int subWidth = (int) (bounds.getWidth() / 2);
		int subHeight = (int) (bounds.getHeight() / 2);
		Point2D corner = bounds.getLocation();
		AlignedBoundingBox bounds1 = new AlignedBoundingBox(corner.getAddition(subWidth, 0), subWidth, subHeight);
		if(bounds1.contains(shape))
			return 1;
		AlignedBoundingBox bounds2 = new AlignedBoundingBox(corner, subWidth, subHeight);
		if(bounds2.contains(shape))
			return 2;
		AlignedBoundingBox bounds3 = new AlignedBoundingBox(corner.getAddition(0, subHeight), subWidth, subHeight);
		if(bounds3.contains(shape))
			return 3;
		AlignedBoundingBox bounds4 = new AlignedBoundingBox(corner.getAddition(subWidth, subHeight), subWidth, subHeight);
		if(bounds4.contains(shape))
			return 4;
		return index;
	}

	/*
	 * Insert the object into the quadtree. If the node exceeds the capacity, it
	 * will split and add all objects to their corresponding nodes.
	 */
	public void insert(BoundingShape shape) {
		if (nodes[0] != null) {
			int index = getIndex(shape);

			if (index != -1) {
				nodes[index].insert(shape);
				return;
			}
		}

		objects.add(shape);

		if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
			if (nodes[0] == null) {
				split();
			}

			int i = 0;
			while (i < objects.size()) {
				int index = getIndex((BoundingShape)objects.get(i));
				if (index != -1) {
					nodes[index].insert((BoundingShape)objects.remove(i));
				} else {
					i++;
				}
			}
		}
	}

	/*
	 * Return all objects that could collide with the given object
	 */
	public List<Object> retrieve(List<Object> returnObjects, BoundingShape shape) {
		int index = getIndex(shape);
		if (index != -1 && nodes[0] != null) {
			nodes[index].retrieve(returnObjects, shape);
		}

		returnObjects.addAll(objects);

		return returnObjects;
	}
}
