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

package elementarymud.client.inputparsing;

import elementarymud.client.ZoneObjects;
import elementarymud.client.inputparsing.actions.Action;
import elementarymud.client.inputparsing.actions.ActionRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public class CommandScanner {

	private static final Set<String> stopWords = new HashSet<>();
	private static final Pattern number = Pattern.compile("[0-9]+");

	private final ZoneObjects zoneObjects;
	private final ActionRepository actions;

	private String verb;
	private String rawInputWithoutVerb;

	LinkedList<String> input;

	static {
		stopWords.add("the");
		stopWords.add("in");
		stopWords.add("of");
		stopWords.add("from");
		stopWords.add("at");
		stopWords.add("it");
		stopWords.add("to");
	}

	public CommandScanner(final ZoneObjects zoneObjects, final ActionRepository actions,
			String rawInput) {
		this.zoneObjects = zoneObjects;
		this.actions = actions;
		input = new LinkedList(Arrays.asList(rawInput.split(" ")));
	}

	public Action firstWordAction() {
		Action action = null; 

		if (!input.isEmpty()) {
			verb = input.peek();
			action = actions.getAction(verb);	

			if (action == null) {
				RPObject bestHit = getBestHitFor(verb, zoneObjects.getExits());
				if (bestHit != null) {
					// we allow to enter direction words directly without a go infront of it
					action = actions.getAction("go");
					verb = "go";
				} 
			} else {
				// in case we actually had an action as the first word, we want to
				// remove that word from the input queue
				input.pop();
			}
		}
		rawInputWithoutVerb = reconcatenate(input);

		return action;
	}

	private RPObject scanIn(Iterable<RPObject> collection) {
		skipStopWords();
		RPObject hit = getBestHitFor(reconcatenate(input), collection);
		if (hit != null) {
			purgeObject(hit);
		}
		return hit;
	}

	public RPObject scanForExit() {
		return scanIn(zoneObjects.getExits());
	}

	public RPObject scanForPlayer() {
		return scanIn(zoneObjects.getPlayers());
	}
	
	public RPObject scanForBagItem() {
		return scanIn(zoneObjects.getMyCharacter().getCharacter().getSlot("bag"));
	}

	public RPObject scanForRPObject() {
		return scanIn(zoneObjects.getAllObjects());
	}

	public String getInputWithoutVerb() {
		return rawInputWithoutVerb;
	}

	public String getVerb() {
		return verb;
	}

	public String getRemainder() {
		return reconcatenate(input);
	}

	private void skipStopWords() {
		String word;
		while (!input.isEmpty()) {
			word = input.peek();
			if (stopWords.contains(word)) {
				input.pop();	
			} else {
				break;
			}
		}
	}

	private void purgeObject(RPObject object) {
		String remainder = reconcatenate(input);
		String objectName = object.get("name").toLowerCase();
		String objectShortName = object.get("shortname").toLowerCase();
		if (remainder.startsWith(objectName)) {
			remainder = remainder.replaceFirst(objectName, "");
		} else {
			// the remainder starts with the short name
			remainder = remainder.replaceFirst(objectShortName, "");
		}

		remainder = remainder.trim();
		if (!remainder.equals("")) {
			input = new LinkedList<>(Arrays.asList(remainder.split(" ")));
		} else {
			input = new LinkedList<>();
		}
	}

	private static String reconcatenate(List<String> list) {
		StringBuilder string = new StringBuilder();
		Iterator<String> iterator = list.listIterator();
		if (iterator.hasNext()) {
			string.append(iterator.next());
			while (iterator.hasNext()) {
				string.append(" ").append(iterator.next());
			}
		}
		return string.toString();
	}


	/**
	 * Gets the best exact hit.
	 * 
	 * @param remainder
	 * @param objects
	 * @return 
	 */
	private RPObject getBestHitFor(String remainder, Iterable<RPObject> objects) {
		int bestHitLength = 0;
		RPObject bestHit = null;
		for (RPObject object : objects) {
			String name = object.get("name").toLowerCase();
			if (remainder.startsWith(name) && name.length() > bestHitLength) {
				bestHitLength = name.length();
				bestHit = object;
			}

			String shortName = object.get("shortname").toLowerCase();
			if (remainder.startsWith(shortName) && shortName.length() > bestHitLength) {
				bestHitLength = shortName.length();
				bestHit = object;
			}

			if (object.hasSlot("bag")) {
				RPObject bestBagHit = getBestHitFor(remainder, object.getSlot("bag"));
				if (bestBagHit != null) {
					String bagHitName = bestBagHit.get("name");
					if (bestHitLength < bagHitName.length()) {
						bestHitLength = bagHitName.length();
						bestHit = bestBagHit;
					}
				
					String bagHitShortName = bestBagHit.get("shortname");
					if (bestHitLength < bagHitShortName.length()) {
						bestHitLength = bagHitShortName.length();
						bestHit = bestBagHit;
					}
				}
			}
		}
		return bestHit;
	}	

}
