package model.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.world.terrain.heightmap.Height;
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
			Map<Height, Point2D> heights = getHeights(); 
			for (Height t : heights.keySet()) {
				t.elevate(getAttenuatedAmplitude(heights.get(t)));
			}
			updateParcelsFor(heights);
		}
	}
	
	private class Low implements Runnable{

		@Override
		public void run() {
			Map<Height, Point2D> heights = getHeights(); 
			for (Height t : heights.keySet()) {
				t.elevate(-getAttenuatedAmplitude(heights.get(t)));
			}
			updateParcelsFor(heights);
		}
	}

	private class Noise implements Runnable{

		@Override
		public void run() {
			Map<Height, Point2D> heights = getHeights(); 
			for (Height t : heights.keySet()) {
				t.elevate(RandomUtil.between(-1.0, 1.0) * getAttenuatedAmplitude(heights.get(t)));
			}
			updateParcelsFor(heights);
		}
	}

	private class Smooth implements Runnable{

		@Override
		public void run() {
			Map<Height, Point2D> heights = getHeights(); 
			for (Height t : heights.keySet()) {
				t.elevate(RandomUtil.between(-1.0, 1.0) * getAttenuatedAmplitude(heights.get(t)));
			}
			updateParcelsFor(heights);
		}
	}

	private class Uniform implements Runnable{
		private final double elevation;
		
		public Uniform() {
			elevation = world.getRegion(coord).getTerrain().getHeightMap().get(coord).getElevation();
		}
		
		@Override
		public void run() {
			Map<Height, Point2D> heights = getHeights(); 
			for (Height t : heights.keySet()) {
				double diff = elevation - t.getElevation();
				double attenuatedAmplitude = getAttenuatedAmplitude(coord);
				if (diff > 0) {
					t.elevate(Math.min(diff, attenuatedAmplitude));
				} else if (diff < 0) {
					t.elevate(Math.max(diff, -attenuatedAmplitude));
				}
			}
			updateParcelsFor(heights);
		}
	}

	private class Reset implements Runnable{

		@Override
		public void run() {
			Map<Height, Point2D> heights = getHeights(); 
			updateParcelsFor(heights);
		}
	}
	
	private double getAttenuatedAmplitude(Point2D p){
		return 1 * getStrength() * getApplicationRatio(p);
	}
	
	private void updateParcelsFor(Map<Height, Point2D>  heights){
		List<Height> res = new ArrayList<>();
		res.addAll(heights.keySet());
		for (Height h : new ArrayList<Height>(heights.keySet())) {
			Region r = world.getRegion(heights.get(h));
			for (Height neib : r.getTerrain().getHeightMap().get8Around(h)) {
				if (!heights.containsKey(neib)) {
					heights.put(neib, r.getTerrain().getHeightMap().getCoord(neib.getIndex()));
				}
			}
		}
		for (Height h : heights.keySet()) {
			Region r = world.getRegion(heights.get(h));
			r.getTerrain().getParcelling().updateParcelsContaining(new ArrayList<Height>(heights.keySet()));
			world.getTerrainDrawer(r).updateParcels(r.getTerrain().getParcelling().getParcelsContaining(new ArrayList<Height>(heights.keySet())));
		}
	}
}
