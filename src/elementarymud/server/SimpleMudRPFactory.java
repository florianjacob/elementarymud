/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementarymud.server;

import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;
import marauroa.server.game.rp.RPObjectFactory;

/**
 *
 * @author raignarok
 */
public class SimpleMudRPFactory extends RPObjectFactory {
	private static final Logger log = Log4J.getLogger(SimpleMudRPFactory.class);

	private static final SimpleMudRPFactory instance = new SimpleMudRPFactory();

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
		if (name.equals("character")) {
			return new SimpleMudCharacter(object);
		} else if (name.equals("zone")) {
			return new ZoneRPObject(object);
		} else {
			return super.transform(object);
		}
	}
	
}
