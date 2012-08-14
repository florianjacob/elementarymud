package elementarymud.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Timer;
import marauroa.client.BannedAddressException;
import marauroa.client.ClientFramework;
import marauroa.client.TimeoutException;
import marauroa.client.net.PerceptionHandler;
import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;
import marauroa.common.net.InvalidVersionException;
import marauroa.common.net.message.MessageS2CPerception;
import marauroa.common.net.message.TransferContent;

/**
 *
 * @author raignarok
 */
public class Client extends ClientFramework implements ActionListener {

	private final PerceptionHandler handler;
	private final PerceptionListener listener;
	private static final Client instance = new Client();
	private final Map<RPObject.ID, RPObject> zoneObjects;
	private String[] availableCharacters;
	private UI ui;
	private MyCharacter myCharacter = new MyCharacter();
	private final Timer timer;
	private static final Logger log = Log4J.getLogger(Client.class);

	public static Client get() {
		return instance;
	}

	private Client() {
		super("log4j.properties");
		zoneObjects = new HashMap<RPObject.ID, RPObject>();
		listener = new PerceptionListener();
		handler = new PerceptionHandler(listener);
	    timer = new Timer(300, this);
		timer.setInitialDelay(500);
	}

	public String[] getAvailableCharacters() {
		return availableCharacters;
	}

	public UI getUI() {
		return ui;
	}

	@Override
	protected void onPerception(MessageS2CPerception message) {
		try {
			handler.apply(message, zoneObjects);
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

	@Override
	public synchronized boolean chooseCharacter(String characterName)
			throws TimeoutException, InvalidVersionException, BannedAddressException {
		boolean successful = super.chooseCharacter(characterName);
		if (successful) {
			myCharacter.setCharacterName(characterName);
		}
		return successful;
	}

	public MyCharacter getMyCharacter() {
		return myCharacter;
	}

	public Map<RPObject.ID, RPObject> getZoneObjects() {
		return zoneObjects;
	}

	/**
	 * Start the network loop of the client and the swing event loop of the UI.
	 * 
	 * @param ui 
	 */
	public void start(final UI ui) {
		this.ui = ui;
		timer.start();
		ui.start();
	}

	/**
	 * Called by the timer to read new messages from the network buffer.
	 * @param ae 
	 */
	@Override
	public void actionPerformed(final ActionEvent ae) {
		loop(0);
	}

	public List<RPObject> getExits() {
		ArrayList<RPObject> exits = new ArrayList<RPObject>(zoneObjects.size());
		for (RPObject object : zoneObjects.values()) {
			if (object.instanceOf(RPClass.getRPClass("exit"))) {
				exits.add(object);	
			}
		}

		if (exits.isEmpty()) {
			return Collections.emptyList();
		}

		return Collections.unmodifiableList(exits);
	}	

	/**
	 * @return a list of everything excluding players, rooms and exits
	 */
	public List<RPObject> getEntities() {
		ArrayList<RPObject> entities = new ArrayList<RPObject>(zoneObjects.size());
		for (RPObject object : zoneObjects.values()) {
			if (!object.instanceOf(RPClass.getRPClass("exit"))
					&& !object.instanceOf(RPClass.getRPClass("character"))
					&& !object.instanceOf(RPClass.getRPClass("zone"))) {
				entities.add(object);	
			}
		}

		if (entities.isEmpty()) {
			return Collections.emptyList();
		}

		return Collections.unmodifiableList(entities);

	}	
}
