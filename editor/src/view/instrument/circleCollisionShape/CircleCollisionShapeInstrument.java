package view.instrument.circleCollisionShape;

import presenter.EditorPlatform;
import presenter.instrument.CircleCollisionShapeInstrumentPresenter;
import util.LogUtil;
import view.controls.JmeImageView;
import view.instrument.InstrumentInputListener;
import view.instrument.planarStance.PlanarStanceInstrumentState;

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
