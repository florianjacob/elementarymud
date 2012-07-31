package elementarymud.server;

import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.Definition;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public class MudRPObject extends RPObject {
	private static final Logger log = Log4J.getLogger(MudRPObject.class);

	public static void generateRPClass() {
		RPClass mudobject = new RPClass("mudobject");
		mudobject.addAttribute("name", Definition.Type.STRING);
		mudobject.addAttribute("description", Definition.Type.STRING);
	}

	public MudRPObject(String name, String description) {
		super();
		setRPClass("mudobject");
		put("name", name);
		put("description", description);
	}

	public MudRPObject(RPObject template) {
		super(template);
		setRPClass("mudobject");
	}
	
}
