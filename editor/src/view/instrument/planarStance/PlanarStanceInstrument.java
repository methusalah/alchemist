package view.instrument.planarStance;

import presenter.EditorPlatform;
import presenter.instrument.PlanarStanceInstrumentPresenter;
import view.controls.JmeImageView;

public class PlanarStanceInstrument {
	private final PlanarStanceInstrumentPresenter presenter;
	private final PlanarStanceInstrumentInputListener inputListener;
	private final JmeImageView jme;
	private PlanarStanceInstrumentState state;
	
	public PlanarStanceInstrument(JmeImageView jme) {
		this.jme = jme;
		presenter = new PlanarStanceInstrumentPresenter(this);
		jme.enqueue(app -> {
			if(app.getStateManager().getState(PlanarStanceInstrumentState.class) == null){
				state = new PlanarStanceInstrumentState(presenter);
			}
			return true;
		});
		inputListener = new PlanarStanceInstrumentInputListener(jme);
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
