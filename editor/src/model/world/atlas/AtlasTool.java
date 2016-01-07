package model.world.atlas;

import java.util.ArrayList;
import java.util.List;

import model.world.PencilTool;
import model.world.Region;
import model.world.WorldData;
import model.world.terrain.atlas.Atlas;
import model.world.terrain.atlas.AtlasLayer;
import util.geometry.geom2d.Point2D;

public class AtlasTool extends PencilTool {
	public enum OPERATION {
		Add_Delete, Propagate_Smooth
	}
	
	private OPERATION operation = OPERATION.Add_Delete;

	public AtlasTool(WorldData world) {
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
			case Add_Delete:
				currentWork = new Increment();
				break;
			case Propagate_Smooth:
				currentWork = new Propagate();
				break;
		}
	}

	@Override
	public void onSecondaryActionStart() {
		switch (operation) {
		case Add_Delete:
			currentWork = new Decrement();
			break;
		case Propagate_Smooth:
			currentWork = new Smooth();
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

	private class Increment implements Runnable{

		@Override
		public void run() {
			List<Region> involvedRegions = new ArrayList<>();
			for (Pixel p : getInvolvedPixels()) {
				for(Region r : world.getRegions(p.worldCoord)){
					if(!involvedRegions.contains(r))
						involvedRegions.add(r);
					
					Atlas toPaint = r.getTerrain().getAtlas();
					AtlasLayer layer = toPaint.getLayers().get(1);
					AtlasArtisanUtil.incrementPixel(toPaint, p.coord.getSubtraction(r.getCoord().getMult(2)), layer, getAttenuatedIncrement(p.worldCoord));
				}
			}
			for(Region r : involvedRegions)
				world.getTerrainDrawer(r).updateAtlas();
		}
	}
	
	private class Decrement implements Runnable{

		@Override
		public void run() {
//			Map<Height, Point2D> heights = getHeights(); 
//			for (Height t : heights.keySet()) {
//				t.elevate(-getAttenuatedAmplitude(heights.get(t)));
//			}
//			updateParcelsFor(heights);
		}
	}

	private class Propagate implements Runnable{

		@Override
		public void run() {
//			Map<Height, Point2D> heights = getHeights(); 
//			for (Height t : heights.keySet()) {
//				t.elevate(RandomUtil.between(-1.0, 1.0) * getAttenuatedAmplitude(heights.get(t)));
//			}
//			updateParcelsFor(heights);
		}
	}

	private class Smooth implements Runnable{

		@Override
		public void run() {
//			Map<Height, Point2D> heights = getHeights(); 
//			for (Height t : heights.keySet()) {
//				t.elevate(RandomUtil.between(-1.0, 1.0) * getAttenuatedAmplitude(heights.get(t)));
//			}
//			updateParcelsFor(heights);
		}
	}

	private double getAttenuatedIncrement(Point2D p){
		return Math.round(30*strength * getApplicationRatio(p));
	}
	
	public List<Pixel> getInvolvedPixels() {
		switch (shape) {
			case Circle:
				return AtlasExplorer.getPixelsInCircle(coord, size / 2);
			case Diamond:
				return AtlasExplorer.getPixelsInDiamond(coord, size / 2);
			case Square:
				return AtlasExplorer.getPixelsInSquare(coord, size / 2);
			default:
				throw new RuntimeException();
		}
	}
}
