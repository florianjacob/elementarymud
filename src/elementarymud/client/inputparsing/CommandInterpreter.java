package elementarymud.client.inputparsing;

import elementarymud.client.Client;
import elementarymud.client.inputparsing.actions.Action;

/**
 * The CommandInterpreter class provides an interface to the whole
 * command parsing subsystem.
 *
 * @author raignarok
 */
public class CommandInterpreter {

	/**
	 * Call this method with the raw input as entered by the user
	 * to parse and execute the command.
	 * If parsing fails, this method prints out errors to the user.
	 * 
	 * @param rawInput the raw input as entered by the user
	 */
	public static void onInput(String rawInput) {
		CommandParser parser = new CommandParser(rawInput);
		Command command = parser.parse();

		if (command != null) {
			Action action = command.getVerb().getAction();
			action.execute(command);
		} else {
			Client.get().getUI().writeln("Try something else.");
		}
	}
}
