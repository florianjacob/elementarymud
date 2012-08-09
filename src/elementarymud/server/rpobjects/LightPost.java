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
public class LightPost extends MudRPObject {
	
	private static final Logger log = Log4J.getLogger(LightPost.class);

	private static final String RPCLASSNAME = "lightpost";	

	public LightPost() {
		super("lightpost", "post", "a mossy iron lightpost. It still works");
		setRPClass(RPCLASSNAME);
	}

	public LightPost(RPObject template) {
		super(template);
	}

	public static void generateRPClass() {
		RPClass lightpost = new RPClass(RPCLASSNAME);
		lightpost.isA(MudRPObject.getRPClassName());
	}

	public static String getRPClassName() {
		return RPCLASSNAME;
	}
}
