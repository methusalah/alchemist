package presentation.instrument.circleCollisionShape;

import model.EditorPlatform;
import presentation.instrument.InstrumentInputListener;
import presentation.scene.customControl.JmeImageView;

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
		if(value && !EditorPlatform.getSceneInputManager().hasListener(inputListener)){
			jme.enqueue(app -> app.getStateManager().attach(state));
			EditorPlatform.getSceneInputManager().addListener(inputListener);
		} else if(!value && EditorPlatform.getSceneInputManager().hasListener(inputListener)){
			jme.enqueue(app -> app.getStateManager().detach(state));
			EditorPlatform.getSceneInputManager().removeListener(inputListener);
		}
	}
}
