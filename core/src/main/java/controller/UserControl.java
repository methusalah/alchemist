package controller;

import util.geometry.geom2d.Point2D;

import com.jme3.app.state.AbstractAppState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class UserControl extends AbstractAppState{
	
	private final SpatialSelector selector = new SpatialSelector();
	private final Node n = new Node();
	
	private Point2D planarCoord = Point2D.ORIGIN;

	public UserControl() {
		n.attachChild(new Geometry("ground", new Box(1000000, 1000000, 0.01f)));
	}
	
	public void setScreenCoord(Point2D coord){
		planarCoord = selector.getCoord(n, coord);
	}
	
	
	
	public Point2D getPlanarCoord() {
		return planarCoord;
	}

	@Override
	public void update(float tpf) {
		
	}

}
