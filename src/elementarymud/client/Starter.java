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

package elementarymud.client;

import elementarymud.client.inputparsing.CommandInterpreter;
import elementarymud.client.inputparsing.actions.ActionRepository;
import java.io.IOException;
import marauroa.client.BannedAddressException;
import marauroa.client.LoginFailedException;
import marauroa.client.TimeoutException;
import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.RPObject;
import marauroa.common.net.InvalidVersionException;

/**
 *
 * @author raignarok
 */
public class Starter {

	private static Logger log = Log4J.getLogger(Starter.class);
	private static ZoneObjects zoneObjects;
	private static Client client;
	private static UI ui;
	private static ActionRepository actions;

	private static boolean initialized = false;
	private static boolean started = false;

	private Starter() {
	}

	protected static void initializeSingletons(UI ui) {
		if (!initialized) {
			zoneObjects = new ZoneObjects();
			actions = new ActionRepository(zoneObjects, ui);
			client = new Client(zoneObjects, ui, actions);
			ui.setCommandInterpreter(new CommandInterpreter(zoneObjects, client, actions));

			Starter.ui = ui;
			initialized = true;
		}
	}

	protected static void start() {
		if (!started) {
			client.start();
			ui.start(zoneObjects.getMyCharacter().getName() + ">");
			started = true;
		}
	}

	/**
	 * @param args 
	 */
	public static void main(String[] args) {
		new Starter().startUp(args);
	}	

	private void startUp(String[] args) {
		if (args.length < 4) {
			printHelpAndExit();	
		}
		String mode = args[0];
		String serveraddress = args[1];
		int port = 5555;
		String username = args[2];
		String password = args[3];

		boolean newUser = false;
		String email = null;

		if (args.length > 4) {
			email = args[4];
			newUser = true;
			log.info("Trying to register as the new user " + username + "...");
		}

		if (serveraddress.contains(":")) {
			port = Integer.parseInt(serveraddress.substring(serveraddress.indexOf(":") + 1));
			serveraddress = serveraddress.substring(0, serveraddress.indexOf(":"));
		}
		log.info("Trying to connect to " + serveraddress + " at port " + port + " as " + username + "...");

		UI ui = null;
		switch (mode) {
			case "terminal":
				ui = new TerminalUI();
				break;
			case "testbot":
				ui = new TestBot();
				break;
			default:
				printHelpAndExit();
				break;
		}

		initializeSingletons(ui);
		try {
			client.connect(serveraddress, port);
			if (newUser) {
				client.createAccount(username, password, email);
				log.info("New user created.");
			}

			client.login(username, password);
			log.info("Login successfull.");

			if (client.getAvailableCharacters().length == 0) {
				RPObject character = new RPObject();
				client.createCharacter(username, character);
				log.info("New character created.");
			}
			
			client.chooseCharacter(username);
		} catch (TimeoutException|InvalidVersionException|BannedAddressException|LoginFailedException|IOException e) {
			log.error("Problem during login and/or character creation: " + e);
			System.exit(1);
		}

		start();
	}

	private static void printHelpAndExit() {
		log.info("Usage: java -jar simplemud.jar terminal|testbot"
				+ " serveraddress[:port] username password [email]");
		System.exit(0);
	}
}
