package model.world;

import java.util.List;
import java.util.Map;

import util.geometry.geom2d.Point2D;
import util.math.RandomUtil;
import model.world.terrain.heightmap.Height;

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
	public void onPrimaryActionEnd() {
		currentWork = null;
	}

	private class Raise implements Runnable{

		@Override
		public void run() {
			Map<Height, Point2D> heights = getHeights(); 
			for (Height t : heights.keySet()) {
				t.elevate(getAttenuatedAmplitude(heights.get(t)));
			}
		}
	}
	private class Noise implements Runnable{

		@Override
		public void run() {
			Map<Height, Point2D> heights = getHeights(); 
			for (Height t : heights.keySet()) {
				t.elevate(RandomUtil.between(-1.0, 1.0) * getAttenuatedAmplitude(heights.get(t)));
			}
		}
	}
	private class Uniform implements Runnable{
		private double elevation;
		@Override
		public void run() {
			Map<Height, Point2D> heights = getHeights(); 
			for (Height t : heights.keySet()) {
				double diff = maintainedElevation - t.getElevation();
				double attenuatedAmplitude = getAttenuatedAmplitude(t.getCoord());
				if (diff > 0) {
					t.elevate(Math.min(diff, attenuatedAmplitude));
				} else if (diff < 0) {
					t.elevate(Math.max(diff, -attenuatedAmplitude));
				}
			}
		}
	}
	
	private double getAttenuatedAmplitude(Point2D p){
		return 1 * getStrength() * getApplicationRatio(p);
	}
}
