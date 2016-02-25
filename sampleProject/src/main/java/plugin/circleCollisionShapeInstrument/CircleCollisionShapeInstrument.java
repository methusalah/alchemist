package plugin.circleCollisionShapeInstrument;

import com.brainless.alchemist.view.ViewPlatform;
import com.brainless.alchemist.view.instrument.InstrumentInputListener;
import com.brainless.alchemist.view.tab.scene.customControl.JmeImageView;

public class CircleCollisionShapeInstrument {
	private final CircleCollisionShapeInstrumentPresenter presenter;
	private final InstrumentInputListener inputListener;
	private final JmeImageView jme;
	private CircleCollisionShapeInstrumentState state;

	public CircleCollisionShapeInstrument(JmeImageView jme) {
		this.jme = jme;
		presenter = new CircleCollisionShapeInstrumentPresenter(this);
		jme.enqueue(app -> {
			state = new CircleCollisionShapeInstrumentState(presenter);
			return true;
		});
		inputListener = new InstrumentInputListener(jme, CircleCollisionShapeInstrumentState.class);
	}
	
	public void setVisible(boolean value){
		if(value && !ViewPlatform.getSceneInputManager().hasListener(inputListener)){
			jme.enqueue(app -> app.getStateManager().attach(state));
			ViewPlatform.getSceneInputManager().addListener(inputListener);
		} else if(!value && ViewPlatform.getSceneInputManager().hasListener(inputListener)){
			jme.enqueue(app -> app.getStateManager().detach(state));
			ViewPlatform.getSceneInputManager().removeListener(inputListener);
		}
	}
}
