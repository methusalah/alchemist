package app;

import java.util.prefs.BackingStoreException;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.system.AppSettings;

public abstract class CosmoVania extends SimpleApplication implements PhysicsTickListener {
	protected MyDebugger debugger;
	private BulletAppState bulletAppState;

	public CosmoVania() {
		showSettings = false;
	}
	
	@Override
	public void start() {
		// set some default settings in-case
		// settings dialog is not shown
		if (true){//settings == null) {
			setSettings(new AppSettings(true));
			settings.setWidth(1024);
			settings.setHeight(768);
			settings.setFrameRate(50);
			try {
				settings.load("openrts.example");
			} catch (BackingStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.start();
	}

	@Override
	public void initialize() {
		
		bulletAppState = new BulletAppState();
		bulletAppState.startPhysics();
		stateManager.attach(bulletAppState);
		getPhysicsSpace().addTickListener(this);
		super.initialize();
		
		debugger = new MyDebugger(0, 1500, assetManager.loadFont("Interface/Fonts/Console.fnt"));
		guiNode.attachChild(debugger.getNode());
		
		stateManager.detach(stateManager.getState(FlyCamAppState.class));
	}

	public void simplePhysicsUpdate(float tpf) {
	}

	@Override
	public void prePhysicsTick(PhysicsSpace arg0, float arg1) {
	}

	@Override
	public void physicsTick(PhysicsSpace space, float f) {
		simplePhysicsUpdate(f);
	}

	public PhysicsSpace getPhysicsSpace() {
		return bulletAppState.getPhysicsSpace();
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		super.update();
	}
}
