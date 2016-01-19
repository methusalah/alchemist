package model.state;

import java.util.ArrayList;
import java.util.List;

import com.jme3.app.state.AbstractAppState;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;

public class InstrumentUpdateState extends AbstractAppState {
	
	private final List<Node> nodes = new ArrayList<>();
	
	@Override
	public void update(float tpf) {
		super.update(tpf);
		for(Node n : nodes)
			n.updateLogicalState(tpf);
	}
	
	@Override
	public void render(RenderManager rm) {
		super.render(rm);
		for(Node n : nodes)
			n.updateGeometricState();
	}
	
	public void addNode(Node n){
		nodes.add(n);
	}

}
