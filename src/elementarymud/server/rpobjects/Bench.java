package elementarymud.server.rpobjects;

import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public class Bench extends MudRPObject {
	private static final Logger log = Log4J.getLogger(Bench.class);

	private static final String RPCLASSNAME = "bench";	

	public Bench() {
		super("bench", "an old and rusty bench made of iron");
		setRPClass(RPCLASSNAME);
	}

	public Bench(RPObject template) {
		super(template);
	}

	public static void generateRPClass() {
		RPClass bench = new RPClass(RPCLASSNAME);
		bench.isA(MudRPObject.getRPClassName());
	}

	public static String getRPClassName() {
		return RPCLASSNAME;
	}	
	
}
