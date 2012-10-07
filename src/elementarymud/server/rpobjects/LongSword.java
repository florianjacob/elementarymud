package elementarymud.server.rpobjects;

import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public class LongSword extends MudRPObject {
	private static final Logger log = Log4J.getLogger(ShortSword.class);

	private static final String RPCLASSNAME = "longsword";

	public LongSword() {
		super("long sword", "sword", "a mighty long sword");
		setRPClass(RPCLASSNAME);
	}

	public LongSword(RPObject template) {
		super(template);
	}

	public static void generateRPClass() {
		RPClass sword = new RPClass(RPCLASSNAME);
		sword.isA(MudRPObject.getRPClassName());
	}

	public static String getRPClassName() {
		return RPCLASSNAME;
	}

	@Override
	public boolean isTakeableBy(Character character) {
		return true;
	}	
}
