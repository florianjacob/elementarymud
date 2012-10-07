package elementarymud.server.rpobjects;

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

	private static final String NAME = "name";
	private static final String SHORTNAME = "shortname";
	private static final String DESCRIPTION = "description";
	private static final String RPCLASSNAME = "mudobject";

	public static void generateRPClass() {
		RPClass mudobject = new RPClass(RPCLASSNAME);
		mudobject.addAttribute(NAME, Definition.Type.STRING);
		mudobject.addAttribute(SHORTNAME, Definition.Type.STRING);
		mudobject.addAttribute(DESCRIPTION, Definition.Type.STRING);
	}

	public MudRPObject(String name, String shortname, String description) {
		super();
		setRPClass(RPCLASSNAME);
		put(NAME, name);
		put(SHORTNAME, shortname);
		put(DESCRIPTION, description);
	}

	public MudRPObject(String name, String description) {
		this(name, name, description);
	}

	public MudRPObject(RPObject template) {
		super(template);
	}

	public boolean isTakeableBy(Character character) {
		return false;
	}

	public static String getRPClassName() {
		return RPCLASSNAME;
	}

	public String getName() {
		return get(NAME);
	}	

	public void setName(String name) {
		put(NAME, name);
	}

	public String getShortName() {
		return get(SHORTNAME);
	}	

	public void setShortName(String shortName) {
		put(SHORTNAME, shortName);
	}

	public String getDescription() {
		return get(DESCRIPTION);
	}

	public void setDescription(String description) {
		put(DESCRIPTION, description);
	}
}
