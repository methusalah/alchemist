package presenter.worldEdition.atlas;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.world.Region;
import model.world.WorldData;
import model.world.terrain.atlas.Atlas;
import model.world.terrain.atlas.AtlasLayer;
import presenter.util.ToggledEnumProperty;
import presenter.worldEdition.PencilToolPresenter;
import presenter.worldEdition.PencilToolPresenter.Shape;
import util.LogUtil;
import util.geometry.geom2d.Point2D;

public class AtlasToolPresenter extends PencilToolPresenter {
	public enum Operation {
		ADD_DELETE, PROPAGATE_SMOOTH
	}

	private final ToggledEnumProperty<Operation> operationProperty = new ToggledEnumProperty<>(Operation.class);

	public AtlasToolPresenter(WorldData world) {
		super(world);
	}

	@Override
	public void doPrimary() {
		switch (operationProperty.getValue()) {
			case ADD_DELETE: increment(); break;
			case PROPAGATE_SMOOTH: propagate(); break;
		}
	}

	@Override
	public void doSecondary() {
		switch (operationProperty.getValue()) {
		case ADD_DELETE: decrement(); break;
		case PROPAGATE_SMOOTH: smooth(); break;
		}
	}
	
	private void increment() {
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
	
	private void decrement() {
//			Map<Height, Point2D> heights = getHeights(); 
//			for (Height t : heights.keySet()) {
//				t.elevate(-getAttenuatedAmplitude(heights.get(t)));
//			}
//			updateParcelsFor(heights);
	}

	private void propagate() {
//			Map<Height, Point2D> heights = getHeights(); 
//			for (Height t : heights.keySet()) {
//				t.elevate(RandomUtil.between(-1.0, 1.0) * getAttenuatedAmplitude(heights.get(t)));
//			}
//			updateParcelsFor(heights);
	}

	private void smooth() {
//			Map<Height, Point2D> heights = getHeights(); 
//			for (Height t : heights.keySet()) {
//				t.elevate(RandomUtil.between(-1.0, 1.0) * getAttenuatedAmplitude(heights.get(t)));
//			}
//			updateParcelsFor(heights);
	}

	private double getAttenuatedIncrement(Point2D p){
		return Math.round(30*getStrengthProperty().getValue() * getApplicationRatio(p));
	}
	
	public List<Pixel> getInvolvedPixels() {
		switch (getShapeProperty().getValue()) {
			case CIRCLE:
				return AtlasExplorer.getPixelsInCircle(coord, getSizeProperty().getValue() / 2);
			case DIAMOND:
				return AtlasExplorer.getPixelsInDiamond(coord, getSizeProperty().getValue() / 2);
			case SQUARE:
				return AtlasExplorer.getPixelsInSquare(coord, getSizeProperty().getValue() / 2);
			default:
				throw new RuntimeException();
		}
	}

	public ToggledEnumProperty<Operation> getOperationProperty() {
		return operationProperty;
	}

}
