package model.ES.component.command;

import java.util.ArrayList;
import java.util.List;

import com.simsilica.es.EntityComponent;

import model.ES.richData.ColorData;

public class PlayerControl implements EntityComponent{
	public ColorData color = new ColorData(255, 150, 0, 0);
	public List<Double>  doubles = new ArrayList<Double>() {{
	    add(1d);
	    add(3d);
	    add(8d);
	}};

	public ColorData getColor() {
		return color;
	}

	public List<Double> getDoubles() {
		return doubles;
	}
}
