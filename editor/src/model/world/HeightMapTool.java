package model.world;

import java.util.ArrayList;
import java.util.List;

import model.world.terrain.heightmap.HeightMapNode;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.math.RandomUtil;

public class HeightMapTool extends PencilTool {
	public enum OPERATION {
		Raise_Low, Noise_Smooth, Uniform_Reset
	}
	
	private OPERATION operation = OPERATION.Raise_Low;

	public HeightMapTool(WorldData world) {
		super(world);
	}

	public OPERATION getOperation() {
		return operation;
	}

	public void setOperation(OPERATION operation) {
		this.operation = operation;
	}
	
	@Override
	public void onPrimaryActionStart() {
		switch (operation) {
			case Raise_Low:
				currentWork = new Raise();
				break;
			case Noise_Smooth:
				currentWork = new Noise();
				break;
			case Uniform_Reset:
				currentWork = new Uniform();
				break;
		}
	}

	@Override
	public void onSecondaryActionStart() {
		switch (operation) {
			case Raise_Low:
				currentWork = new Low();
				break;
			case Noise_Smooth:
				currentWork = new Smooth();
				break;
			case Uniform_Reset:
				currentWork = new Reset();
				break;
		}
	}
	
	@Override
	public void onPrimaryActionEnd() {
		currentWork = null;
	}

	@Override
	public void onSecondaryActionEnd() {
		currentWork = null;
	}

	private class Raise implements Runnable{

		@Override
		public void run() {
			List<Point2D> points = getHeights();
			for (Point2D p : points) {
				for(HeightMapNode n : world.getHeights(p)){
					n.elevate(getAttenuatedAmplitude(p));
				}
			}
			updateParcelsFor(points);
		}
	}
	
	private class Low implements Runnable{

		@Override
		public void run() {
			List<Point2D> points = getHeights(); 
			for (Point2D p : points) {
				for(HeightMapNode n : world.getHeights(p))
					n.elevate(-getAttenuatedAmplitude(p));
			}
			updateParcelsFor(points);
		}
	}

	private class Noise implements Runnable{

		@Override
		public void run() {
			List<Point2D> points = getHeights(); 
			for (Point2D p : points) {
				double randomElevation = RandomUtil.between(-1.0, 1.0) * getAttenuatedAmplitude(p);
				for(HeightMapNode n : world.getHeights(p))
					n.elevate(randomElevation);
			}
			updateParcelsFor(points);
		}
	}

	private class Smooth implements Runnable{

		@Override
		public void run() {
			List<Point2D> points = getHeights(); 
			for (Point2D p : points) {
				for(HeightMapNode n : world.getHeights(p)){
					double average = 0;
					for (HeightMapNode neib : world.get4HeightsAround(p)) {
						average += neib.getElevation();
					}
					average /= world.get4HeightsAround(p).size();
		
					double diff = average - n.getElevation();
					if (diff > 0) {
						n.elevate(Math.min(diff, getAttenuatedAmplitude(p)));
					} else if (diff < 0) {
						n.elevate(Math.max(diff, -getAttenuatedAmplitude(p)));
					}
				}
			}
			updateParcelsFor(points);
		}
	}

	private class Uniform implements Runnable{
		private final double elevation;
		
		public Uniform() {
			HeightMapNode h = world.getHeights(coord).get(0);
			if(h != null)
				elevation = h.getElevation();
			else
				elevation = 0;
		}
		
		@Override
		public void run() {
			List<Point2D> points = getHeights(); 
			for (Point2D p : points) {
				for(HeightMapNode n : world.getHeights(p)){
					double diff = elevation - n.getElevation();
					double attenuatedAmplitude = getAttenuatedAmplitude(p);
					if (diff > 0) {
						n.elevate(Math.min(diff, attenuatedAmplitude));
					} else if (diff < 0) {
						n.elevate(Math.max(diff, -attenuatedAmplitude));
					}
				}
			}
			updateParcelsFor(points);
		}
	}

	private class Reset implements Runnable{

		@Override
		public void run() {
			List<Point2D> points = getHeights(); 
			for (Point2D p : points) {
				for(HeightMapNode n : world.getHeights(p)){
					n.setElevation(0);
				}
			}
			updateParcelsFor(points);
		}
	}
	
	private double getAttenuatedAmplitude(Point2D p){
		return 1 * getStrength() * getApplicationRatio(p);
	}
	
	private void updateParcelsFor(List<Point2D> points){
		List<Region> regions = new ArrayList<>();
		for(Point2D p : points){
			for(Region r : world.getRegions(p))
				if(!regions.contains(r))
					regions.add(r);
		}
		List<HeightMapNode> nodes = new ArrayList<>();
		for(Point2D p : points){
			for(HeightMapNode n : world.get4HeightsAround(p))
				if(!nodes.contains(n))
					nodes.add(n);
		}
		for (Region r : regions) {
			r.getTerrain().getParcelling().updateParcelsContaining(nodes);
			world.getTerrainDrawer(r).updateParcels(r.getTerrain().getParcelling().getParcelsContaining(nodes));
		}
	}
}
