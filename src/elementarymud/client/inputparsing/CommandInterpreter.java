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
