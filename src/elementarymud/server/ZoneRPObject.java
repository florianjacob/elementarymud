package elementarymud.server;

import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public class ZoneRPObject extends MudRPObject {

	public static final String RPCLASSNAME = "zone";

	public ZoneRPObject(String name, String description) {
		super(name, description);
		setRPClass(RPCLASSNAME);
	}

	public ZoneRPObject(RPObject template) {
		super(template);
	}

	public static void generateRPClass() {
		RPClass zoneClass = new RPClass(RPCLASSNAME);
		zoneClass.isA(MudRPObject.getRPClassName());
	}

	public static String getRPClassName() {
		return RPCLASSNAME;
	}
}
