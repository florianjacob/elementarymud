package elementarymud.server;

import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public class ZoneRPObject extends SimpleMudRPObject {

	public ZoneRPObject(String name, String description) {
		super(name, description);
		setRPClass("zone");
	}

	public ZoneRPObject(RPObject template) {
		super(template);
		setRPClass("zone");
	}

	public static void generateRPClass() {
		RPClass zoneClass = new RPClass("zone");
		zoneClass.isA("mudobject");
	}
}
