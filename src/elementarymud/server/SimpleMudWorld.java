package elementarymud.server;

import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.RPClass;
import marauroa.server.game.rp.RPWorld;

/**
 *
 * @author raignarok
 */
public class SimpleMudWorld extends RPWorld {
	private static final SimpleMudWorld instance = new SimpleMudWorld();
	private static final Logger log = Log4J.getLogger(SimpleMudWorld.class);

	//TODO: andere get() falsch implementiert serverseitig?
	//TODO: im Tutorial verdammt nochmal ändern! initialize() der Elternklasse muss aufgerufen werden.
	public static SimpleMudWorld get() {
		return instance;
	}	

	//TODO: It's somewhat strange to have onInit() which gets called by the RPManager on creation and
	//initialize() which I have to call by myself. Maybe onInit() isn't allowed to block for long?
	@Override
	public void onInit() {
		super.onInit();
		super.initialize();
		
		PublicTextEvent.generateRPClass();
		PrivateTextEvent.generateRPClass();

		SimpleMudRPObject.generateRPClass();
		SimpleMudCharacter.generateRPClass();
		ZoneRPObject.generateRPClass();
		RPClass.bakeAll();

		SimpleMudRPZone zone = new SimpleMudRPZone("room", "Plain Room", "a nondescript room");
		addRPZone(zone);
	}
}
