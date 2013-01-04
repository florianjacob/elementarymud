package elementarymud.server.rpobjects;

import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public class Goblin extends MudRPObject {
		private static final Logger log = Log4J.getLogger(Bench.class);

	private static final String RPCLASSNAME = "goblin";	

	public Goblin() {
		super("goblin", "an angry-looking goblin");
		setRPClass(RPCLASSNAME);
	}

	public Goblin(RPObject template) {
		super(template);
	}

	public static void generateRPClass() {
		RPClass goblin = new RPClass(RPCLASSNAME);
		goblin.isA(MudRPObject.getRPClassName());
	}

	public static String getRPClassName() {
		return RPCLASSNAME;
	}
}
