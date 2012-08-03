package elementarymud.server.rpobjects;

import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.Definition;
import marauroa.common.game.IRPZone;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public class Exit extends MudRPObject {
	private static final Logger log = Log4J.getLogger(Exit.class);

	private static final String RPCLASSNAME = "exit";
	private static final String TARGETZONEID = "targetzoneid";

	public Exit(String name, String description, IRPZone.ID targetZoneId) {
		super(name, description);
		setRPClass(RPCLASSNAME);
		put(TARGETZONEID, targetZoneId.getID());
	}

	public Exit(RPObject template) {
		super(template);
	}

	public IRPZone.ID getTargetZoneId() {
		return new IRPZone.ID(get(TARGETZONEID));
	}

	public static void generateRPClass() {
		RPClass exit = new RPClass(RPCLASSNAME);
		exit.isA(MudRPObject.getRPClassName());
		exit.addAttribute(TARGETZONEID, Definition.Type.STRING);
	}

	public static String getRPClassName() {
		return RPCLASSNAME;
	}
}
