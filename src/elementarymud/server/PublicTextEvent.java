/**
 * This file is part of ElementaryMUD.
 *
 * ElementaryMUD is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ElementaryMUD is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ElementaryMUD. If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2012 Florian Jacob
 */

package elementarymud.server;

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
