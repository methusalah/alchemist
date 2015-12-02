package presenter;

import javafx.application.Platform;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import model.world.HeightMapTool;
import model.world.PopulationTool;
import model.world.Tool;
import model.world.WorldData;
import model.world.atlas.AtlasTool;
import util.LogUtil;
import util.event.EntitySelectionChanged;
import util.event.EventManager;
import util.geometry.geom2d.Point2D;
import application.topDownScene.SceneInputListener;
import application.topDownScene.state.SceneSelectorState;
import application.topDownScene.state.WorldToolState;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3x.jfx.injfx.JmeForImageView;
import com.simsilica.es.EntityId;

public class WorldEditorPresenter implements SceneInputListener {
	private static enum ActionType {StartPrimary,
		StartSecondary,
		StopPrimary,
		StopSecondary,
		OncePrimary,
		OnceSecondary
	}
	
	
	private final JmeForImageView jme;
	private final WorldData worldData;
	private boolean hasTool = false;
	
	private final HeightMapTool heightmapTool;
	private final AtlasTool atlasTool;
	private final PopulationTool populationTool;

	public WorldEditorPresenter(JmeForImageView jme, WorldData worldData) {
		this.jme = jme;
		this.worldData = worldData;
		EventManager.register(this);
		
		heightmapTool = new HeightMapTool(worldData);
		atlasTool = new AtlasTool(worldData);
		populationTool = new PopulationTool(worldData);
	}

	public HeightMapTool getHeightmapTool() {
		return heightmapTool;
	}

	public AtlasTool getAtlasTool() {
		return atlasTool;
	}

	public PopulationTool getPopulationTool() {
		return populationTool;
	}
	
	public void selectTool(Tool tool){
		LogUtil.info("select tool : "+tool);
		hasTool = tool != null;
		if(tool != null)
			jme.enqueue(app -> setTool(app, tool));
	}
	
	public void saveWorld(){
		worldData.saveDrawnRegions();
	}
	
	@Override
	public void onMousePressed(MouseEvent e){
		if(hasTool){
			ActionType type;
			switch(e.getButton()){
			case PRIMARY : type = ActionType.StartPrimary; break;
			case SECONDARY : type = ActionType.StartSecondary; break;
			default : type = null;
			}
			if(type != null);
				jme.enqueue(app -> setToolAction(app, type));
		}
	}

	@Override
	public void onMouseMoved(MouseEvent e){
		jme.enqueue(app -> setSceneMouseCoord(app, new Point2D(e.getX(), e.getY())));
	}

	@Override
	public void onMouseReleased(MouseEvent e){
		if(hasTool){
			switch(e.getButton()){
			case PRIMARY :
				jme.enqueue(app -> setToolAction(app, ActionType.StopPrimary));
				jme.enqueue(app -> setToolAction(app, ActionType.OncePrimary));
				break;
			case SECONDARY : 
				jme.enqueue(app -> setToolAction(app, ActionType.StopSecondary));
				jme.enqueue(app -> setToolAction(app, ActionType.OnceSecondary));
				break;
			default:
				break;
			}
		} else {
			jme.enqueue(app -> selectEntity(app));
		}
	}
	
	@Override
	public void onMouseScroll(ScrollEvent e){
	}

	@Override
	public void onKeyPressed(KeyEvent e){
	}
	
	@Override
	public void onKeyReleased(KeyEvent e){
	}
	
	static private boolean setSceneMouseCoord(SimpleApplication app, Point2D coord) {
		AppStateManager stateManager = app.getStateManager();
		stateManager.getState(SceneSelectorState.class).setCoordInScreenSpace(coord);
		return true;
	}

	static private boolean setToolAction(SimpleApplication app, ActionType type) {
		Tool t = app.getStateManager().getState(WorldToolState.class).getTool();
		switch(type){
		case OncePrimary : t.onPrimarySingleAction(); break;
		case OnceSecondary : t.onSecondarySingleAction(); break;
		case StartPrimary : t.onPrimaryActionStart(); break;
		case StartSecondary : t.onSecondaryActionStart(); break;
		case StopPrimary : t.onPrimaryActionEnd(); break;
		case StopSecondary : t.onSecondaryActionEnd(); break;
		}
		return true;
	}

	static private boolean setTool(SimpleApplication app, Tool t) {
		AppStateManager stateManager = app.getStateManager();
		stateManager.getState(WorldToolState.class).setTool(t);
		
		return true;
	}

	static private boolean selectEntity(SimpleApplication app) {
		AppStateManager stateManager = app.getStateManager();
		EntityId pointed = stateManager.getState(SceneSelectorState.class).getPointedEntity();
		if(pointed != null)
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					EventManager.post(new EntitySelectionChanged(pointed));
				}
			});
		return true;
	}


	@Override
	public void onMouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
