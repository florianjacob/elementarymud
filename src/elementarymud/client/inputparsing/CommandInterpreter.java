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
		CommandScanner scanner = new CommandScanner(rawInput);
		Action action = scanner.firstWordAction();

		if (action != null) {
			boolean valid = action.configure(scanner);
			if (valid) {
				action.execute();
			} else {
				//TODO: Display command hint here? Or do that in the Action itself?
				Client.get().getUI().writeln("Try something else.");
			}
		} else {
			Client.get().getUI().writeln("Try something else.");
		}
	}
}
