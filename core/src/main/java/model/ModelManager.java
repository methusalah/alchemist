package model;

import java.util.logging.Logger;

import util.entity.Entity;
import util.entity.EntityPool;
import util.event.MapResetEvent;
import util.event.EventManager;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;
import model.ES.component.motion.PlanarInertia;
import model.ES.component.motion.PlanarMotionCapacity;
import model.ES.component.motion.PlanarPosition;
import model.battlefield.Battlefield;
import model.battlefield.BattlefieldFactory;
import model.builders.MapArtisanUtil;
import model.builders.entity.definitions.DefParser;

public class ModelManager {

	public static final String CONFIG_PATH = "assets/data";
	public static final String DEFAULT_MAP_PATH = "assets/maps/";
	private static final double UPDATE_DELAY = 1000;
	private static final int DEFAULT_WIDTH = 64;
	private static final int DEFAULT_HEIGHT = 32;

	private static final BattlefieldFactory factory;

	private static Battlefield battlefield;
	private final static DefParser parser;
	private static double nextUpdate = 0;
	public static boolean battlefieldReady = true;
	public static Entity ship;

	static {
		ship = EntityPool.createEntity("player ship");
		ship.add(new PlanarPosition(new Point2D(1, 1), 0.5));
		ship.add(new PlanarInertia(0));
		ship.add(new PlanarMotionCapacity(2, AngleUtil.toRadians(360), 2, 2));

		parser = new DefParser(CONFIG_PATH);
		factory = new BattlefieldFactory();
		// setNewBattlefield();
	}

	// no instancing from outside
	private ModelManager() {
	}

	public static void updateConfigs() {
		if (System.currentTimeMillis() > nextUpdate) {
			nextUpdate = System.currentTimeMillis() + UPDATE_DELAY;
			parser.readFile();
		}
	}

	public static void loadBattlefield() {
		Battlefield loadedBattlefield = factory.loadWithFileChooser();
		setBattlefield(loadedBattlefield);
	}

	public static void loadBattlefield(String file) {
		Battlefield loadedBattlefield = factory.load(file);
		setBattlefield(loadedBattlefield);
	}

	public static void saveBattlefield() {
		factory.save(battlefield);
	}

	public static void setNewBattlefield() {
		setBattlefield(factory.getNew(DEFAULT_WIDTH, DEFAULT_HEIGHT));
	}

	private static void setBattlefield(Battlefield battlefield) {
		if (battlefield != null) {
			ModelManager.battlefield = battlefield;
			battlefieldReady = true;
			MapArtisanUtil.act(getBattlefield().getMap());
			getBattlefield().getEngagement().reset();
			EventManager.post(new MapResetEvent());
		}
	}

	public static void reload() {
		saveBattlefield();
		Battlefield loadedBattlefield = factory.load(battlefield.getFileName());
		setBattlefield(loadedBattlefield);
	}

	public static Battlefield getBattlefield() {
		if(battlefieldReady) {
			return battlefield;
		} else {
			throw new RuntimeException("Trying to acces to battlefield while it is unavailable");
		}
	}

	public static void setBattlefieldUnavailable(){
		battlefieldReady = false;
	}
	public static void setBattlefieldReady(){
		battlefieldReady = true;
	}

}
