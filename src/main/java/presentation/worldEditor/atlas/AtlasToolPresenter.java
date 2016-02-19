package presentation.worldEditor.atlas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import model.tempImport.RendererPlatform;
import presentation.common.ToggledEnumProperty;
import presentation.worldEditor.PencilToolPresenter;
import processor.logic.world.WorldProc;
import util.geometry.geom2d.Point2D;

public class AtlasToolPresenter extends PencilToolPresenter {
	public enum Operation {
		ADD_DELETE, PROPAGATE_SMOOTH
	}

	private final AtlasViewable view;
	
	private final ListProperty<TerrainTexture> textures = new SimpleListProperty<>();
	private final ToggledEnumProperty<Operation> operationProperty = new ToggledEnumProperty<>(Operation.class);
	private final IntegerProperty actualTextureProperty = new SimpleIntegerProperty();
	private Region lastRegion;
	
	
	public AtlasToolPresenter(AtlasViewable view) {
		this.view = view;
		textures.addListener((ListChangeListener.Change<? extends TerrainTexture> c) -> {
			if(lastRegion != null){
				lastRegion.getTerrain().getTexturing().clear();
				lastRegion.getTerrain().getTexturing().addAll(textures.getValue());
				getWorld().getTerrainDrawer(lastRegion).updateTexturing();
			}
			view.updateTextureGrid();
		});
	}

	@Override
	public void doPrimary() {
		switch (operationProperty.getValue()) {
			case ADD_DELETE: increment(); break;
			case PROPAGATE_SMOOTH: propagate(); break;
		}
		if(coord == null){
			lastRegion = null;
			textures.set(null);
		} else if(getWorld().getRegionsAtOnce(coord).get(0) != lastRegion){
			lastRegion = getWorld().getRegionsAtOnce(coord).get(0);
			textures.set(FXCollections.observableArrayList(lastRegion.getTerrain().getTexturing()));
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
			for(Region r : getWorld().getRegionsAtOnce(p.worldCoord)){
				if(!involvedRegions.contains(r))
					involvedRegions.add(r);
				
				Atlas toPaint = r.getTerrain().getAtlas();
				AtlasLayer layer = toPaint.getLayers().get(actualTextureProperty.getValue());
				AtlasArtisanUtil.incrementPixel(toPaint, p.coord.getSubtraction(r.getCoord().getMult(2)), layer, getAttenuatedIncrement(p.worldCoord));
			}
		}
		for(Region r : involvedRegions){
			r.setModified(true);
			getWorld().getTerrainDrawer(r).updateAtlas();
		}
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
		if(coord == null)
			return new ArrayList<Pixel>();
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

	public ListProperty<TerrainTexture> getTextures() {
		return textures;
	}
	
	public void editTerrainTexture(int index){
		TerrainTexture toEdit;
		if(textures.size() <= index || textures.get(index) == null)
			toEdit = new TerrainTexture("", "", 0);
		else
			toEdit = textures.get(index);
		Optional<TerrainTexture> edited = view.showTerrainTextureDialog(toEdit);
		if(edited.isPresent()){
			// First we must complete the list with null values if the index is out of bounds
			while(textures.size() <= index)
				textures.add(null);
			textures.set(index, edited.get());
		}
	}

	public void deleteTerrainTexture(int index){
		textures.set(index, null);
	}

	public IntegerProperty getActualTextureProperty() {
		return actualTextureProperty;
	}
	private WorldProc getWorld() {
		return RendererPlatform.getStateManager().getState(WorldProc.class);
	}
}
