package plugin.infiniteWorld.editor.presentation;

import java.util.ArrayList;
import java.util.List;

import ECS.processor.logic.world.WorldProc;
import model.EditorPlatform;
import model.tempImport.RendererPlatform;
import plugin.infiniteWorld.world.Region;
import plugin.infiniteWorld.world.terrain.heightmap.HeightMapNode;
import presentation.common.ToggledEnumProperty;
import util.geometry.geom2d.Point2D;
import util.math.RandomUtil;

public class HeightmapConfiguratorPresenter extends PencilConfiguratorPresenter {
	
	public enum Operation {
		RAISE_LOW, NOISE_SMOOTH, UNIFORM_RESET
	}
	
	private final ToggledEnumProperty<Operation> operationProperty = new ToggledEnumProperty<>(Operation.class);

	private double elevation = Double.NaN;

	@Override
	public void doPrimary(){
		switch (operationProperty.getValue()) {
		case RAISE_LOW: raise(); break;
		case NOISE_SMOOTH: noise(); break;
		case UNIFORM_RESET:	uniformize(); break;
		}
	}
	
	@Override
	public void doSecondary(){
		switch (operationProperty.getValue()) {
		case RAISE_LOW: lower(); break;
		case NOISE_SMOOTH: smooth(); break;
		case UNIFORM_RESET: reset(); break;
		}
	}

	@Override
	public void begin() {
		elevation = Double.NaN;
	}

	private void raise(){
		List<Point2D> points = getNodes();
		for (Point2D p : points) {
			for(HeightMapNode n : getWorld().getHeights(p)){
				n.elevate(getAttenuatedAmplitude(p));
			}
		}
		updateParcelsFor(points);
	}
	
	private void lower() {
		List<Point2D> points = getNodes(); 
		for (Point2D p : points) {
			for(HeightMapNode n : getWorld().getHeights(p))
				n.elevate(-getAttenuatedAmplitude(p));
		}
		updateParcelsFor(points);
	}

	private void noise() {
		List<Point2D> points = getNodes(); 
		for (Point2D p : points) {
			double randomElevation = RandomUtil.between(-1.0, 1.0) * getAttenuatedAmplitude(p);
			for(HeightMapNode n : getWorld().getHeights(p))
				n.elevate(randomElevation);
		}
		updateParcelsFor(points);
	}

	private void smooth() {
		List<Point2D> points = getNodes(); 
		for (Point2D p : points) {
			for(HeightMapNode n : getWorld().getHeights(p)){
				double average = 0;
				for (HeightMapNode neib : getWorld().get4HeightsAround(p)) {
					average += neib.getElevation();
				}
				average /= getWorld().get4HeightsAround(p).size();
	
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

	private void uniformize() {
		if(Double.isNaN(elevation)){
			HeightMapNode h = getWorld().getHeights(coord).get(0);
			if(h != null)
				elevation = h.getElevation();
		}
		if(!Double.isNaN(elevation)){
			List<Point2D> points = getNodes(); 
			for (Point2D p : points) {
				for(HeightMapNode n : getWorld().getHeights(p)){
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

	private void reset() {
		List<Point2D> points = getNodes(); 
		for (Point2D p : points) {
			for(HeightMapNode n : getWorld().getHeights(p)){
				n.setElevation(0);
			}
		}
		updateParcelsFor(points);
	}
	
	private double getAttenuatedAmplitude(Point2D p){
		return 3*getStrengthProperty().getValue() * getApplicationRatio(p);
	}
	
	private void updateParcelsFor(List<Point2D> points){
		List<Region> regions = new ArrayList<>();
		for(Point2D p : points){
			for(Region r : getWorld().getRegionsAtOnce(p))
				if(!regions.contains(r))
					regions.add(r);
		}
		List<HeightMapNode> nodes = new ArrayList<>();
		for(Point2D p : points){
			for(HeightMapNode n : getWorld().get4HeightsAround(p))
				if(!nodes.contains(n))
					nodes.add(n);
		}
		for (Region r : regions) {
			r.setModified(true);
			synchronized (r.getTerrain().getParcelling()) {
				r.getTerrain().getParcelling().updateParcelsContaining(nodes);
			}
			EditorPlatform.getScene().enqueue(() -> {
				synchronized (r.getTerrain().getParcelling()) {
					getWorld().getTerrainDrawer(r).updateParcels(r.getTerrain().getParcelling().getParcelsContaining(nodes));
				}
			});
		}
	}

	public ToggledEnumProperty<Operation> getOperationProperty() {
		return operationProperty;
	}
	
	private WorldProc getWorld() {
		return RendererPlatform.getStateManager().getState(WorldProc.class);
	}

	
}
