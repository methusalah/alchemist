package presenter;

import model.Command;
import model.EditorPlatform;
import model.ECS.TraversableEntityData;
import presenter.util.UserComponentList;

public class OverviewPresenter {
	
	public OverviewPresenter() {
		EditorPlatform.setEntityData(new TraversableEntityData());
		EditorPlatform.setCommand(new Command());
	}

	public void stopScene(){
		EditorPlatform.getScene().stop(false);
	}
	
	public void setComponentList(UserComponentList compList){
		EditorPlatform.getUserComponentList().setValue(compList);
	}
}
