/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementarymud.client;

import marauroa.common.game.Definition;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPEvent;

/**
 *
 * @author raignarok
 */
public class PublicTextEvent extends RPEvent {
		
	/**
	 * Creates the rpclass.
	 */
	public static void generateRPClass() {
		final RPClass rpclass = new RPClass("public_text");
		rpclass.add(Definition.DefinitionClass.ATTRIBUTE, "text", Definition.Type.LONG_STRING);
	}

	/**
	 * Creates a new text event.
	 *
	 * @param text Text
	 */
	public PublicTextEvent(final String text) {
		super("public_text");		
		put("text", text);
	}
}
