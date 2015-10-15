package util.event;


public class AddComponentEvent extends Event {
	public final String compName;
	
	public AddComponentEvent(String compName) {
		this.compName = compName;
	}
}
