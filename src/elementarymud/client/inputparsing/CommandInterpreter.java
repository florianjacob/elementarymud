package elementarymud.client.inputparsing;

import elementarymud.client.ZoneObjects;
import elementarymud.client.inputparsing.actions.Action;
import elementarymud.client.inputparsing.actions.ActionRepository;
import marauroa.client.ClientFramework;
import marauroa.common.game.RPAction;

/**
 * The CommandInterpreter class provides an interface to the whole
 * command parsing subsystem.
 *
 * @author raignarok
 */
public class CommandInterpreter {

	private final ClientFramework client;
	private final ZoneObjects zoneObjects;
	private final ActionRepository actions;

	public CommandInterpreter(ZoneObjects zoneObjects, ClientFramework client,
			ActionRepository actions) {
		this.zoneObjects = zoneObjects;
		this.client = client;
		this.actions = actions;
	}

	/**
	 * Call this method with the raw input as entered by the user
	 * to parse and execute the command.
	 * If parsing fails, this method prints out errors to the user.
	 * 
	 * @param rawInput the raw input as entered by the user
	 * @return true when the command entered actually existed
	 */
	public boolean onInput(String rawInput) {
		CommandScanner scanner = new CommandScanner(zoneObjects, actions, rawInput);
		Action action = scanner.firstWordAction();

		if (action != null) {
			boolean valid = action.configure(scanner);
			if (valid) {
				RPAction rpAction = action.execute();
				if (rpAction != null) {
					// actions can return rpactions, which are then sent to the server
					client.send(rpAction);
				}
				return true;
			}
		}
		return false;
	}
}
