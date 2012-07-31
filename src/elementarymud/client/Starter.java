package elementarymud.client;

import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public class Starter {

	private static Logger log = Log4J.getLogger(Starter.class);

	/**
	 * @param args 
	 */
	public static void main(String[] args) {
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

		Client client = Client.get();
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
			client.setCharacterName(username);
		} catch (Exception e) {
			log.error("Problem during login and/or character creation: " + e);
			System.exit(1);
		}

		UI ui = null;
		if (mode.equals("terminal")) {
			ui = new TerminalUI();
		} else if (mode.equals("testbot")) {
			ui = new TestBot();
		} else {
			printHelpAndExit();
		}
		client.start(ui);
	}	

	private static void printHelpAndExit() {
		log.info("Usage: java -jar simplemud.jar terminal|testbot"
				+ " serveraddress[:port] username password [email]");
		System.exit(0);
	}
}
