/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementarymud.server;

import marauroa.common.game.Definition;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPEvent;

/**
 *
 * @author raignarok
 */
public class PrivateTextEvent extends RPEvent {
		
	/**
	 * Creates the rpclass.
	 */
	public static void generateRPClass() {
		final RPClass rpclass = new RPClass("private_text");
		rpclass.add(Definition.DefinitionClass.ATTRIBUTE, "text", Definition.Type.LONG_STRING);
	}

	/**
	 * Creates a new text event.
	 *
	 * @param text Text
	 */
	public PrivateTextEvent(final String text) {
		super("private_text");		
		put("text", text);
	}
}
