package brainless.alchemist.components;
import brainless.alchemist.annotation.UIDescription;

import com.simsilica.es.EntityComponent;

@UIDescription(description="this is just a testcomponent, which contains a description at a class", name="testcomponent")
public class TestComponent implements EntityComponent {

	@UIDescription(description="this ist field description", name="name")
	public String name;
	
	
	
}
