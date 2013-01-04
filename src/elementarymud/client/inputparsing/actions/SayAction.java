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

import elementarymud.client.inputparsing.CommandScanner;
import java.util.HashSet;
import java.util.Set;
import marauroa.common.game.RPAction;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
class SayAction extends Action {

	private String usedVerb;
	private String wordsToSpeak;

	@Override
	public RPAction execute() {
		RPAction action = new RPAction();
		action.put("verb", usedVerb);

		if (hasTarget()) {
			action.put("target", getTargetID().getObjectID());
		}
		action.put("words", wordsToSpeak);

		return action;
	}

	@Override
	public Set<String> actionVerbs() {
		Set<String> verbs = new HashSet<>();
		verbs.add("say");
		verbs.add("sayto");
		verbs.add("tell");
		return verbs;

	}

	@Override
	public boolean configure(CommandScanner scanner) {
		usedVerb = scanner.getVerb();
		if (!usedVerb.equals("say")) {
			// tell and sayto (the other say actions) have a target object
			RPObject target = scanner.scanForPlayer();
			if (target != null) {
				setTarget(target);
				wordsToSpeak = scanner.getRemainder();
				return true;
			} else {
				getUI().writeln("Usage: tell <playername> <message>");
				return false;
			}
		} else {
			wordsToSpeak = scanner.getInputWithoutVerb();
			return true;
		}
	}
}
