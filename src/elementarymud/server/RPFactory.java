package elementarymud.server;

import elementarymud.server.rpobjects.ZoneRPObject;
import elementarymud.server.rpobjects.Character;
import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;
import marauroa.server.game.rp.RPObjectFactory;

/**
 *
 * @author raignarok
 */
public class RPFactory extends RPObjectFactory {
	private static final Logger log = Log4J.getLogger(RPFactory.class);

	private static final RPFactory instance = new RPFactory();

	/**
	 * returns the factory instance (this method is called
	 * by Marauroa using reflection).
	 * 
	 * @return RPObjectFactory
	 */
	public static RPObjectFactory getFactory() {
		return instance;
	}

	@Override
	public RPObject transform(RPObject object) {
		
		final RPClass clazz = object.getRPClass();
		if (clazz == null) {
			log.error("Cannot create concrete object for " + object
					+ " because it does not have an RPClass.");
			return super.transform(object);
		}

		final String name = clazz.getName();
		if (name.equals(Character.getRPClassName())) {
			return new Character(object);
		} else if (name.equals(ZoneRPObject.getRPClassName())) {
			return new ZoneRPObject(object);
		} else {
			return super.transform(object);
		}
	}
	
}
