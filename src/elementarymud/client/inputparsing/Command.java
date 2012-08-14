package elementarymud.client.inputparsing;

/**
 * A Command is the analyzed and parsed result of the raw user input.
 * It consists of a verb, and optionally of an object or an unparsed remainder.
 * 
 * As an example, consider the raw input:
 * say raignarok Hello to you!
 * 
 * say will be the verb of the command, raignarok the object
 * and "Hello to you!" the unparsed remainder.
 *
 * @author raignarok
 */
public class Command {
	private Word verb;
	private Word object;
	private String remainder;

	public Command(final Word verb) {
		this.verb = verb;
	}

	public Word getVerb() {
		return verb;
	}

	public boolean hasObject() {
		return object != null;
	}

	public Word getObject() {
		return object;
	}

	public void setObject(final Word object) {
		this.object = object;
	}

	public boolean hasRemainder() {
		return remainder != null;
	}

	public String getRemainder() {
		return remainder;
	}

	public void setRemainder(final String remainder) {
		this.remainder = remainder;
	}
}
