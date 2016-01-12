package presenter;

import com.simsilica.es.EntityId;

import model.state.GripState;
import presenter.common.GripInputListener;

public class GripPresenter {
	GripInputListener inputListener;
	
	public GripPresenter() {
		inputListener = new GripInputListener(EditorPlatform.getScene());
		EditorPlatform.getScene().enqueue(app -> {
			app.getStateManager().attach(new GripState(EditorPlatform.getEntityData()));
			return true;
		});
		
		
		EditorPlatform.getSelectionProperty().addListener((observable, oldValue, newValue)-> {
			if(newValue != null)
				activateGrip(newValue.getEntityId());
			else
				desactivateGrip();
		});
	}
	
	private void activateGrip(EntityId eid){
		EditorPlatform.getSceneInputManager().addListener(inputListener);
		EditorPlatform.getScene().enqueue(app -> {
			app.getStateManager().getState(GripState.class).attach(eid);
			return true;
		});
	}
	
	private void desactivateGrip(){
		EditorPlatform.getSceneInputManager().removeListener(inputListener);
		EditorPlatform.getScene().enqueue(app -> {
			app.getStateManager().getState(GripState.class).detach();
			return true;
		});
	}
}
