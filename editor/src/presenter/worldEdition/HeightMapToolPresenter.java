package presenter.worldEdition;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.world.Region;
import model.world.WorldData;
import model.world.terrain.heightmap.HeightMapNode;
import presenter.EditorPlatform;
import presenter.util.ToggledEnumProperty;
import presenter.worldEdition.atlas.AtlasToolPresenter.Operation;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.math.RandomUtil;

public class HeightMapToolPresenter extends PencilToolPresenter {
	
	public enum Operation {
		RAISE_LOW, NOISE_SMOOTH, UNIFORM_RESET
	}
	
	private final ToggledEnumProperty<Operation> operationProperty = new ToggledEnumProperty<>(Operation.class);

	private double elevation = Double.NaN;

	public void doPrimary(){
		switch (operationProperty.getValue()) {
		case RAISE_LOW: raise(); break;
		case NOISE_SMOOTH: noise(); break;
		case UNIFORM_RESET:	uniformize(); break;
		}
	}
	
	public void doSecondary(){
		switch (operationProperty.getValue()) {
		case RAISE_LOW: lower(); break;
		case NOISE_SMOOTH: smooth(); break;
		case UNIFORM_RESET: reset(); break;
		}
	}
	
	public void doNothing(){
		elevation = Double.NaN;
	}

	private void raise(){
		List<Point2D> points = getNodes();
		for (Point2D p : points) {
			for(HeightMapNode n : world.getHeights(p)){
				n.elevate(getAttenuatedAmplitude(p));
			}
		}
		updateParcelsFor(points);
	}
	
	private void lower() {
		List<Point2D> points = getNodes(); 
		for (Point2D p : points) {
			for(HeightMapNode n : world.getHeights(p))
				n.elevate(-getAttenuatedAmplitude(p));
		}
		updateParcelsFor(points);
	}

	private void noise() {
		List<Point2D> points = getNodes(); 
		for (Point2D p : points) {
			double randomElevation = RandomUtil.between(-1.0, 1.0) * getAttenuatedAmplitude(p);
			for(HeightMapNode n : world.getHeights(p))
				n.elevate(randomElevation);
		}
		updateParcelsFor(points);
	}

	private void smooth() {
		List<Point2D> points = getNodes(); 
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

	private void uniformize() {
		if(Double.isNaN(elevation)){
			HeightMapNode h = world.getHeights(coord).get(0);
			if(h != null)
				elevation = h.getElevation();
		}
		if(!Double.isNaN(elevation)){
			List<Point2D> points = getNodes(); 
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

	private void reset() {
		List<Point2D> points = getNodes(); 
		for (Point2D p : points) {
			for(HeightMapNode n : world.getHeights(p)){
				n.setElevation(0);
			}
		}
		updateParcelsFor(points);
	}
	
	private double getAttenuatedAmplitude(Point2D p){
		return 1 * getStrengthProperty().getValue() * getApplicationRatio(p);
	}
	
	private void updateParcelsFor(List<Point2D> points){
		List<Region> regions = new ArrayList<>();
		for(Point2D p : points){
			for(Region r : world.getRegionsAtOnce(p))
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
			r.setModified(true);
			synchronized (r.getTerrain().getParcelling()) {
				r.getTerrain().getParcelling().updateParcelsContaining(nodes);
			}
			EditorPlatform.getScene().enqueue(() -> {
				synchronized (r.getTerrain().getParcelling()) {
					world.getTerrainDrawer(r).updateParcels(r.getTerrain().getParcelling().getParcelsContaining(nodes));
				}
			});
		}
	}

	public ToggledEnumProperty<Operation> getOperationProperty() {
		return operationProperty;
	}

	
}
