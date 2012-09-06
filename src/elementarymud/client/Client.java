package elementarymud.client;

import elementarymud.client.inputparsing.actions.ActionRepository;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Timer;
import marauroa.client.BannedAddressException;
import marauroa.client.ClientFramework;
import marauroa.client.TimeoutException;
import marauroa.client.net.PerceptionHandler;
import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.net.InvalidVersionException;
import marauroa.common.net.message.MessageS2CPerception;
import marauroa.common.net.message.TransferContent;

/**
 * This class acts as turning point for Client-Server-Communication.
 * It provides methods to send messages to the server and delegates
 * handling of incoming perceptions to the PerceptionListener.
 * 
 * Additionally, it holds references to commonly-needed
 * objects as the used UI, the objects in the current zone and
 * the MyCharacter object.
 *
 * @author raignarok
 */
public class Client extends ClientFramework implements ActionListener {

	private final PerceptionHandler handler;
	private final PerceptionListener listener;
	private String[] availableCharacters;
	private final Timer timer;
	private final ZoneObjects zoneObjects;
	private final UI ui;
	private static final Logger log = Log4J.getLogger(Client.class);

	protected Client(ZoneObjects zoneObjects, UI ui, ActionRepository actions) {
		super("log4j.properties");
		this.zoneObjects = zoneObjects;
		this.ui = ui;
		listener = new PerceptionListener(zoneObjects, ui, actions);
		handler = new PerceptionHandler(listener);
	    timer = new Timer(300, this);
		timer.setInitialDelay(500);
	}

	/**
	 * After client.login() was called, this returns all available
	 * character names for the user. This is an empty array if the
	 * user didn't create a character so far.
	 * 
	 * Before login, this returns null.
	 * @return the available chararacter names or null
	 */
	public String[] getAvailableCharacters() {
		return availableCharacters;
	}

	/**
	 * Start the network loop of the client.
	 * This has to be called after the client.login() and client.chooseCharacter()
	 * have been called to start the client event loop.
	 * It repeatingly checks for new server messages and handles them.
	 * 
	 */
	public void start() {
		timer.start();
	}

	/**
	 * After login allows you to choose a character to play
	 *
	 * @param characterName
	 *            name of the character we want to play with.
	 * @return true if choosing character is successful.
	 * @throws InvalidVersionException
	 *             if we are not using a compatible version
	 * @throws TimeoutException
	 *             if timeout happens while waiting for the message.
	 * @throws BannedAddressException
	 */	
	@Override
	public synchronized boolean chooseCharacter(String characterName)
			throws TimeoutException, InvalidVersionException, BannedAddressException {
		boolean successful = super.chooseCharacter(characterName);
		if (successful) {
			zoneObjects.getMyCharacter().setName(characterName);
		}
		return successful;
	}

	/**
	 * Called by the timer to read new messages from the network buffer.
	 * No need to call it yourself.
	 * @param ae 
	 */
	@Override
	public void actionPerformed(final ActionEvent ae) {
		loop(0);
	}

	/**
	 * Gets called by the network loop when new perceptions from the server are availalbe.
	 * Delegates the perception handling to the PerceptionListener.
	 * 
	 * @param message the message from the server
	 */
	@Override
	protected void onPerception(MessageS2CPerception message) {
		try {
			handler.apply(message, zoneObjects.getInternalObjectsMap());
		} catch (Exception e) {
			// Something weird happened while applying perception
			e.printStackTrace();
		}
	}

	@Override
	protected List<TransferContent> onTransferREQ(List<TransferContent> items) {
		return items;
	}

	@Override
	protected void onTransfer(List<TransferContent> items) {
	}

	@Override
	protected void onAvailableCharacters(final String[] characters) {
		availableCharacters = characters;
	}

	@Override
	protected void onServerInfo(final String[] info) {
		for (String s : info) {
			ui.writeln(s);
		}
	}

	@Override
	protected String getGameName() {
		return "Chat";
	}

	@Override
	protected String getVersionNumber() {
		return "0.5";
	}

	@Override
	protected void onPreviousLogins(List<String> previousLogins) {
	}
}
