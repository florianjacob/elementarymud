package elementarymud.client.inputparsing;

import elementarymud.client.inputparsing.actions.Action;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public class Word {

	private String word;
	private WordType type;
	private RPObject associatedObject;
	private Action associatedAction;

	protected Word(String word, WordType type, RPObject associatedObject) {
		this.word = word;
		this.type = type;
		this.associatedObject = associatedObject;
	}

	protected Word(String word, WordType type) {
		this.word = word;
		this.type = type;
	}

	protected Word(String word, WordType type, Action action) {
		this.word = word;
		this.type = type;
		this.associatedAction = action;
	}

	public String getWord() {
		return word;
	}

	public WordType getType() {
		return type;
	}

	public RPObject getRPObject() {
		return associatedObject;
	}

	public Action getAction() {
		return associatedAction;
	}

	public enum WordType {

		DIRECTION,
		VERB,
		STOPWORD,
		NOUN,
		NUMBER,
		ERROR;
	}
}
