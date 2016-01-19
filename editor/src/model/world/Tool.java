package model.world;

import controller.ECS.SceneSelectorState;

public abstract class Tool {

	protected SceneSelectorState selector;
	
	public Tool() {
		super();
	}

	public void setSelector(SceneSelectorState selector){
		this.selector = selector;
	};
	
	public void onPrimarySingleAction() {}
	public void onPrimaryActionStart() {}
	public void onPrimaryActionEnd() {}
	public void onSecondarySingleAction() {}
	public void onSecondaryActionStart() {}
	public void onSecondaryActionEnd() {}
	public void doPrimary(){}
	public void doSecondary(){}
	public void onUpdate(float elapsedTime) {}

}