package elementarymud.client.inputparsing;

import elementarymud.client.Client;
import elementarymud.client.inputparsing.Word.WordType;
import java.util.HashSet;
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
	private static final Set<String> stopWords = new HashSet<String>();
	private static final Pattern number = Pattern.compile("[0-9]+");

	static {
		stopWords.add("the");
		stopWords.add("in");
		stopWords.add("of");
		stopWords.add("from");
		stopWords.add("at");
		stopWords.add("it");
	}

	public static LinkedList<Word> scan(String command) {
		String[] input = command.split(" ");	
		LinkedList<Word> result = new LinkedList<Word>();
		for (String word : input) {
			// we store all words in lowercase and cannot do something
			// like .containsIgnoreCase(), so transform to lowercase
			word = word.toLowerCase();
			RPObject associatedObject;
			Action associatedAction;
			if (stopWords.contains(word)) {
				result.add(new Word(word, WordType.STOPWORD));
			} else if ((associatedAction = getAction(word)) != null) {
				result.add(new Word(word, WordType.VERB, associatedAction));
			} else if ((associatedObject = getDirection(word)) != null) {
				result.add(new Word(word, WordType.DIRECTION, associatedObject));
			} else if ((associatedObject = getNoun(word)) != null) {
				result.add(new Word(word, WordType.NOUN, associatedObject));
			} else if (number.matcher(word).matches()) {
				result.add(new Word(word, WordType.NUMBER));
			} else {
				result.add(new Word(word, WordType.ERROR));
			}
		}
		return result;
	}	

	private static RPObject getNoun(String noun) {
		List<RPObject> entities = Client.get().getEntities();
		for (RPObject entity : entities) {
			if (entity.get("name").equalsIgnoreCase(noun)) {
				return entity;
			}
		}
		return null;
	}

	private static RPObject getDirection(String direction) {
		List<RPObject> exits = Client.get().getExits();
		for (RPObject exit : exits) {
			if (exit.get("name").equalsIgnoreCase(direction)) {
				return exit;
			}
		}
		return null;
	}

	private static Action getAction(String action) {
		return Client.get().getCommandInterpreter().getAction(action);
	}
}
