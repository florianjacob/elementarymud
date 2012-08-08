package elementarymud.client.inputparsing;

import elementarymud.client.Client;
import elementarymud.client.inputparsing.Word.WordType;
import java.util.LinkedList;

/**
 *
 * @author raignarok
 */
public class Command {
	private Word verb;
	private Word object;
	private String remainder;

	public Command(Word verb, Word object) {
		this.verb = verb;
		this.object = object;
	}

	public Command(Word verb, String remainder) {
		this.verb = verb;
		this.remainder = remainder;
	}

	public Command(Word verb, Word object, String remainder) {
		this.verb = verb;
		this.object = object;
		this.remainder = remainder;
	}

	public Word getVerb() {
		return verb;
	}

	public Word getObject() {
		return object;
	}

	public String getRemainder() {
		return remainder;
	}

	public boolean hasObject() {
		return object != null;
	}


	private static Word match(LinkedList<Word> words, WordType expecting) {
		if (words != null && !words.isEmpty()) {
			Word word = words.pop();
			if (word.getType().equals(expecting)) {
				return word;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	private static void skip(LinkedList<Word> words, WordType type) {
		while (words != null && !words.isEmpty() && words.peek().getType().equals(type)) {
			words.pop();
		}
	}

	private static Word parseVerb(LinkedList<Word> words) {
		skip(words, WordType.STOPWORD);
		if (words.peek().getType().equals(WordType.VERB)) {
			return match(words, WordType.VERB);
		} else {
			throw new IllegalStateException("Expected a verb next.");
		}
	}

	private static Word parseObject(LinkedList<Word> words) {
		skip(words, WordType.STOPWORD);
		Word next = words.peek();
		if (next == null) {
			return null;
		}

		if (next.getType().equals(WordType.NOUN)) {
			return match(words, WordType.NOUN);
		} else if (next.getType().equals(WordType.DIRECTION)) {
			return match(words, WordType.DIRECTION);
		} else {
			// it's valid that a command has no object
			return null;
		}
	}

	private static String parseRemainder(Word verb, Word object, String rawInput) {
		String lastWord;
		if (object != null) {
			lastWord = object.getWord();
		} else {
			// verb is always != null, and if we have no object, it's the last semantic word
			lastWord = verb.getWord();
		}
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

	public static Command parseCommand(LinkedList<Word> words, String rawInput) {
		skip(words, WordType.STOPWORD);
		Word start = words.peek();

		if (start.getType().equals(WordType.VERB)) {
			// the subject is always the player
			Word verb = parseVerb(words);
			Word object = parseObject(words);
			String remainder = parseRemainder(verb, object, rawInput);
			return new Command(verb, object, remainder);
		} else {
			throw new IllegalStateException("Must start with verb not: " + start.getWord());
		}
	}

}
