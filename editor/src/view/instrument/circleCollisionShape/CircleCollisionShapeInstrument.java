package view.instrument.circleCollisionShape;

import presenter.EditorPlatform;
import presenter.instrument.CircleCollisionShapeInstrumentPresenter;
import util.LogUtil;
import view.controls.JmeImageView;
import view.instrument.planarStance.PlanarStanceInstrumentState;

public class CircleCollisionShapeInstrument {
	private final CircleCollisionShapeInstrumentPresenter presenter;
	private final CircleCollisionShapeInstrumentInputListener inputListener;
	private final JmeImageView jme;
	private CircleCollisionShapeInstrumentState state;

	public CircleCollisionShapeInstrument(JmeImageView jme) {
		this.jme = jme;
		presenter = new CircleCollisionShapeInstrumentPresenter(this);
		jme.enqueue(app -> {
			state = new CircleCollisionShapeInstrumentState(presenter);
			return true;
		});
		inputListener = new CircleCollisionShapeInstrumentInputListener(jme);
	}
	
	public void setVisible(boolean value){
		jme.enqueue(app -> {
			if(value)
				app.getStateManager().attach(state);
			else
				app.getStateManager().detach(state);
			return true;
		});
		if(value)
			EditorPlatform.getSceneInputManager().addListener(inputListener);
		else
			EditorPlatform.getSceneInputManager().removeListener(inputListener);
	}
}
