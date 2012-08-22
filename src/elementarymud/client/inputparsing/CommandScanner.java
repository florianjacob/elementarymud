package elementarymud.client.inputparsing;

import elementarymud.client.ZoneObjects;
import elementarymud.client.inputparsing.Word.WordType;
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

	static {
		stopWords.add("the");
		stopWords.add("in");
		stopWords.add("of");
		stopWords.add("from");
		stopWords.add("at");
		stopWords.add("it");
		stopWords.add("to");
	}

	public static LinkedList<Word> scan(String rawInput) {
		LinkedList<String> input = new LinkedList(Arrays.asList(rawInput.split(" ")));
		LinkedList<Word> result = new LinkedList<>();
		ActionRepository actions = ActionRepository.get();

		String word;
		while (!input.isEmpty()) {
			word = input.peek();
			String remainder = reconcatenate(input);
			RPObject target;

			if (stopWords.contains(word)) {
				result.add(new Word(word, WordType.STOPWORD));
				input.pop();
			} else if (actions.hasAction(word)) {
				result.add(new Word(word, WordType.VERB, actions.getAction(word)));
				input.pop();
			} else if ((target = getDirection(remainder)) != null) {
				input = purgeObject(remainder, target);
				result.add(new Word(target.get("name"), WordType.DIRECTION, target));
			} else if ((target = getNoun(remainder)) != null) {
				input = purgeObject(remainder, target);
				result.add(new Word(target.get("name"), WordType.NOUN, target));
			} else if (number.matcher(word).matches()) {
				input.pop();
				result.add(new Word(word, WordType.NUMBER));
			} else {
				input.pop();
				result.add(new Word(word, WordType.ERROR));
			}
		}
		return result;
	}

	private static LinkedList<String> purgeObject(String remainder, RPObject object) {
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
			return new LinkedList<>(Arrays.asList(remainder.split(" ")));
		} else {
			return new LinkedList<>();
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

	private static RPObject getNoun(String remainder) {
		return getBestHit(ZoneObjects.get().getEntities(), remainder);
	}

	private static RPObject getDirection(String remainder) {
		return getBestHit(ZoneObjects.get().getExits(), remainder);
	}

	private static RPObject getBestHit(List<RPObject> objects, String remainder) {
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
		}
		return bestHit;
	}

}
