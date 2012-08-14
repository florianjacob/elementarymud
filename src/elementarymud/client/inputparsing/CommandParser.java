package elementarymud.client.inputparsing;

import elementarymud.client.inputparsing.Word.WordType;
import elementarymud.client.inputparsing.actions.Action;
import elementarymud.client.inputparsing.actions.ActionRepository;
import java.util.LinkedList;

/**
 * The CommandParser is responsible of
 * taking the raw user input, forwarding it to the CommandScanner
 * and parse the scanned result to a Command object ready to be executed.
 *
 * @author raignarok
 */
public class CommandParser {
	private LinkedList<Word> words;
	private String rawInput;

	/**
	 * 
	 * @param rawInput the rawInput as entered by the user
	 */
	protected CommandParser(String rawInput) {
		this.rawInput = rawInput.trim().toLowerCase();
		words = CommandScanner.scan(rawInput);
	}

	public void skip(WordType type) {
		while (!words.isEmpty() && words.peek().getType().equals(type)) {
			words.pop();
		}
	}

	public Word nextVerb() {
		skip(WordType.STOPWORD);
		if (!words.isEmpty() && words.peek().getType().equals(WordType.VERB)) {
			return words.pop();
		} else {
			return null;
		}
	}

	public Word nextObject() {
		skip(WordType.STOPWORD);
		Word nextObject = null;
		if (!words.isEmpty()) {
			WordType nextType = words.peek().getType();
			if (nextType.equals(WordType.NOUN) || nextType.equals(WordType.DIRECTION)) {
				nextObject = words.pop();
			}
		}
		return nextObject;
	}

	public String getUnparsedRemainder(Word lastParsedWord) {
		String lastWord = lastParsedWord.getWord();
		
		// everything past the last word and its space character is the remainder
		// there might be notihng more..
		int lastWordIndex = rawInput.indexOf(lastWord) + lastWord.length();
		if (lastWordIndex + 1 < rawInput.length() - 1) {
			// return the substring if there's at least one character left
			return rawInput.substring(rawInput.indexOf(lastWord) + lastWord.length() + 1);
		} else {
			return null;
		}
	}

	/**
	 * parse() starts to parse the raw user input provided at object creation
	 * and returns its result as a Commadn object ready to be executed.
	 * 
	 * Following inputs schemes are accepted:
	 *  * the name or short name of an exit
	 *  * a verb
	 *  * a verb, followed by the name of an exit or the name of another object in the zone
	 *  * a verb, followed by the name of an exit or the name of another object in the zone
	 * 		and an arbitrary remainder which will be left unparsed in the command
	 *  * a verb followed by an arbitrary remainder which will be left unparsed in the command
	 * 
	 * Stop words are ignored, meaning "look at the door" will result in the same command
	 * as "look door".
	 * 
	 * @return the parsed Command ready to be executed or null if the input was invalid.
	 */
	protected Command parse() {
		Word verb = nextVerb();

		Action action;
		if (verb != null) {
			action = verb.getAction();
		} else if (words.peek().getType().equals(Word.WordType.DIRECTION)) {
			// we allow to enter direction words directly without a go infront of it
			action = ActionRepository.get().getAction("go");
			verb = new Word("go", Word.WordType.VERB, action);
		} else {
			// no posibility to get a valid command
			return null;
		}
		Command command = new Command(verb);
		action.parse(this, command);

		return command;
	}
}
