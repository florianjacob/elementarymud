/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementarymud.server.rpobjects;

import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public class Sword extends MudRPObject {
	private static final Logger log = Log4J.getLogger(Sword.class);

	private static final String RPCLASSNAME = "sword";

	public Sword() {
		super("sword", "a mighty sword");
		setRPClass(RPCLASSNAME);
	}

	public Sword(RPObject template) {
		super(template);
	}

	public static void generateRPClass() {
		RPClass sword = new RPClass(RPCLASSNAME);
		sword.isA(MudRPObject.getRPClassName());
	}

	public static String getRPClassName() {
		return RPCLASSNAME;
	}
}
