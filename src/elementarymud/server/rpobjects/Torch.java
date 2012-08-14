package elementarymud.server.rpobjects;

import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public class Torch extends MudRPObject {
	private static final Logger log = Log4J.getLogger(Torch.class);

	private static final String RPCLASSNAME = "torch";
	
	public Torch() {
		super("torch", "an unlit torch");
		setRPClass(RPCLASSNAME);
	}

	public Torch(RPObject template) {
		super(template);
	}

	public static void generateRPClass() {
		RPClass torch = new RPClass(RPCLASSNAME);
		torch.isA(MudRPObject.getRPClassName());
	}

	public static String getRPClassName() {
		return RPCLASSNAME;
	}	
}
