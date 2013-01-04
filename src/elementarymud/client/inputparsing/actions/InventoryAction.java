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

package elementarymud.client.inputparsing.actions;

import elementarymud.client.MyCharacter;
import elementarymud.client.inputparsing.CommandScanner;
import java.util.HashSet;
import java.util.Set;
import marauroa.common.game.RPAction;
import marauroa.common.game.RPObject;
import marauroa.common.game.RPSlot;

/**
 *
 * @author raignarok
 */
public class InventoryAction extends Action {

	@Override
	public Set<String> actionVerbs() {
		Set<String> verbs = new HashSet<>();
		verbs.add("inventory");
		verbs.add("i");
		return verbs;
	}

	@Override
	public boolean configure(CommandScanner scanner) {
		// nothing to do here..
		return true;
	}

	@Override
	public RPAction execute() {
		MyCharacter myCharacter = getZoneObjects().getMyCharacter();
		RPSlot bag = myCharacter.getCharacter().getSlot("bag");
		StringBuilder builder = new StringBuilder();
		builder.append("You have with you: \n");
		for (RPObject object : bag) {
			builder.append(object.get("description")).append("\n");
		}
		getUI().write(builder.toString());

		return null;
	}
	
}
