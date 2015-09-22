package model;

public class ModelManager {

	public static final String CONFIG_PATH = "assets/data";
	public static final String DEFAULT_MAP_PATH = "assets/maps/";
	public static boolean battlefieldReady = true;
	public static Command command = new Command();

	// no instancing from outside
	private ModelManager() {
	}

	public static void updateConfigs() {
	}
}
