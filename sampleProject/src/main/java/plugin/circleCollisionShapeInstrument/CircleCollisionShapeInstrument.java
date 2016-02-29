package plugin.circleCollisionShapeInstrument;

import com.brainless.alchemist.model.tempImport.RendererPlatform;
import com.brainless.alchemist.view.ViewPlatform;
import com.brainless.alchemist.view.instrument.InstrumentInputListener;
import com.brainless.alchemist.view.tab.scene.customControl.JmeImageView;

public class CircleCollisionShapeInstrument {
	private final CircleCollisionShapeInstrumentPresenter presenter;
	private final InstrumentInputListener inputListener;
	private CircleCollisionShapeInstrumentState state;

	public CircleCollisionShapeInstrument() {
		presenter = new CircleCollisionShapeInstrumentPresenter(this);
		RendererPlatform.enqueue(app -> {
			state = new CircleCollisionShapeInstrumentState(presenter);
		});
		inputListener = new InstrumentInputListener(CircleCollisionShapeInstrumentState.class);
	}
	
	public void setVisible(boolean value){
		if(value && !ViewPlatform.getSceneInputManager().hasListener(inputListener)){
			RendererPlatform.enqueue(app -> app.getStateManager().attach(state));
			ViewPlatform.getSceneInputManager().addListener(inputListener);
		} else if(!value && ViewPlatform.getSceneInputManager().hasListener(inputListener)){
			RendererPlatform.enqueue(app -> app.getStateManager().detach(state));
			ViewPlatform.getSceneInputManager().removeListener(inputListener);
		}
	}
}
