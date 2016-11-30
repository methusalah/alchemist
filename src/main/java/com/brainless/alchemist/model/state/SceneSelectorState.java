package com.brainless.alchemist.model.state;

import com.brainless.alchemist.model.tempImport.RendererPlatform;
import com.brainless.alchemist.model.tempImport.TranslateUtil;
import com.jme3.app.state.AbstractAppState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.simsilica.es.EntityId;

import util.geometry.geom2d.Point2D;

/**
 * Scene selector offers convenient methods to find what is under the mouse pointer.
 * 
 * If it is pluged to the renderer, and the renderer has an input manager, then the
 * mouse pointer coordinate is updated automaticaly. In other cases, the mouse pointer
 * coordinate must be set manually.  
 * @author benoit
 *
 */
public class SceneSelectorState extends AbstractAppState {
	private final Node plan = new Node("plan");

	private Point2D coordInScreenSpace = Point2D.ORIGIN;

	public SceneSelectorState() {
		plan.attachChild(new Geometry("plan geometry", new Box(1000000, 1000000, 0.001f)));
	}
	
	/**
	 * Called by state manager. This state can be used detached, but in this case the mouse
	 * coordinate won't be updated.
	 */
	@Override
	public void update(float tpf) {
		if(RendererPlatform.getInputManager() != null)
			coordInScreenSpace = TranslateUtil.toPoint2D(RendererPlatform.getInputManager().getCursorPosition());
	}
	
	/**
	 * Returns the coordinate in the plan (x, y) that is pointed by the mouse pointer.
	 * @return
	 */
	public Point2D getPointedCoordInPlan(){
		return SpatialSelector.getCoord(plan, coordInScreenSpace);
	}

	/**
	 * Returns the coordinate in the plan (x, y) that is under the given screen coordinate.
	 * @return
	 */
	public Point2D getPointedCoordInPlan(Point2D coordInScreenSpace){
		return SpatialSelector.getCoord(plan, coordInScreenSpace);
	}
	
	/**
	 * Returns the entity that is registered in the current pointed spatial in the main scene node, if any. Entity id must have been
	 * saved as long value in the spatial user data for this method to work.
	 * 
	 * Note that this method look for the first entity in the spatial parent hierarchy.
	 * 
	 * @return the pointed entity's id, or null if no entity can be found.
	 */
	public EntityId getPointedEntity(){
		Spatial s = SpatialSelector.getPointedGeometry(RendererPlatform.getMainSceneNode(), coordInScreenSpace);
		while(s != null){
			if(s.getUserData("EntityId") != null){
				return new EntityId(s.getUserData("EntityId"));
			}
			s = s.getParent();
		}
		return null;
	}

	/**
	 * Returns the geometry pointed by the mouse pointer in the main scene node, if any.
	 * 
	 * @return the pointed geometry, or null if no geometry can be found.
	 */
	public Geometry getPointedGeometry(){
		return SpatialSelector.getPointedGeometry(RendererPlatform.getMainSceneNode(), coordInScreenSpace);
	}
	
	/**
	 * Returns the actual pointed coordinate in screen space. This getter makes the scene selector
	 * a good place to store and retrieve this info from any app state.
	 * @return
	 */
	public Point2D getCoordInScreenSpace() {
		return coordInScreenSpace;
	}
	
	/**
	 * Manually set the screen coordinate of the mouse pointer.
	 * 
	 * This method is used in the Alchemist interface. It becomes useless if the scene selector is
	 * attached to renderer app state manager and the renderer have an iput manager because in thos case, the mouse
	 * pointer coordinate is updated automatically.  
	 * @param coord
	 */
	public void setCoordInScreenSpace(Point2D coord){
		coordInScreenSpace = coord;
	}

}
