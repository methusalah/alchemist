package model.ES.component.visuals;

import com.simsilica.es.EntityComponent;

public class ModelRotation implements EntityComponent {
	private final double xPeriod, yPeriod, zPeriod;
	
	public ModelRotation() {
		xPeriod = 0;
		yPeriod = 0;
		zPeriod = 0;
	}
	
	public ModelRotation(double xPeriod, double yPeriod, double zPeriod){
		this.xPeriod = xPeriod;
		this.yPeriod = yPeriod;
		this.zPeriod = zPeriod;
	}

	public double getxPeriod() {
		return xPeriod;
	}

	public double getyPeriod() {
		return yPeriod;
	}

	public double getzPeriod() {
		return zPeriod;
	}
	
	

}
