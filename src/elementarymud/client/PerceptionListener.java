package elementarymud.client;

import java.util.Map;
import marauroa.client.net.IPerceptionListener;
import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPEvent;
import marauroa.common.game.RPObject;
import marauroa.common.net.message.MessageS2CPerception;

public class PerceptionListener implements IPerceptionListener {
	private static final Logger log = Log4J.getLogger(PerceptionListener.class);

	@Override
	public boolean onAdded(RPObject object) {
		MyCharacter myCharacter = Client.get().getMyCharacter();
		log.info("onAdded: " + object);
		if (myCharacter.isCharacter(object)) {
			myCharacter.setCharacter(object);
		} else if (object.instanceOf(RPClass.getRPClass("zone"))) {
			myCharacter.setZone(object);
		} else if (object.instanceOf(RPClass.getRPClass("character")) && !myCharacter.hasChangedZone()) {
			Client.get().getUI().writeln(object.get("name") + " entered the " + myCharacter.getZone().get("name") + ".");
		}
		return false;
	}

	@Override
	public boolean onModifiedAdded(RPObject object, RPObject changes) {
		MyCharacter myCharacter = Client.get().getMyCharacter();
		log.info("onModifiedAdded: " + object + " changes: " + changes);
		for (RPEvent event : changes.events()) {
			if (event.getName().equals("public_text")) {
				String prefix = myCharacter.isCharacter(object) ? "You say: " : object.get("name") + " says: ";
				Client.get().getUI().writeln(prefix + event.get("text"));
			}
		}
		return false;
	}

	@Override
	public boolean onModifiedDeleted(RPObject object, RPObject changes) {
		log.info("onModifiedDeleted: " + object + " changes: " + changes);
		return false;
	}

	@Override
	public boolean onDeleted(RPObject object) {
		Map<RPObject.ID, RPObject> zoneObjects = Client.get().getZoneObjects();
		log.info("onDeleted: " + object);
		if (object.instanceOf(RPClass.getRPClass("character"))) {
			RPObject character = zoneObjects.get(object.getID());
			Client.get().getUI().writeln(character.get("name") + " left the " + Client.get().getMyCharacter().getZone().get("name") + ".");
		}
		return false;
	}

	@Override
	public boolean onMyRPObject(RPObject added, RPObject deleted) {
		// onMyRPObject gets called everytime our own RPObject changes so we know that it changed
		// but this happens after onAdded() was already called with our RPObject and all public attributes
		// added and deleted don't contain any public attributes except id and zone
		// they only contain the private added and deleted attributes / events
		UI ui = Client.get().getUI();
		MyCharacter myCharacter = Client.get().getMyCharacter();
		Map<RPObject.ID, RPObject> zoneObjects = Client.get().getZoneObjects();
		RPObject.ID id = null;
		if (added != null) {
			id = added.getID();

			for (RPEvent event : added.events()) {
				if (event.getName().equals("private_text")) {
					ui.writeln("<server> " + event.get("text"));
				}
			}

		}
		if (deleted != null) {
			id = deleted.getID();
		}

		if (id != null) {
			myCharacter.setCharacter(zoneObjects.get(id));
			log.info("onMyRPObject: added: " + added + " deleted: " + deleted);
		}

		if (myCharacter.hasChangedZone()) {
			// execute a look command everytime we get in a new zone and the zone is synced completely
			Client.get().getCommandInterpreter().onInput("look");
			myCharacter.zoneChangeHandled();
		}
		return false;
	}

	@Override
	public boolean onClear() {
		log.info("Object store cleared.");
		return false;
	}

	@Override
	public void onSynced() {
		log.info("Synced.");
	}

	@Override
	public void onUnsynced() {
		log.info("Out of sync!");
	}

	@Override
	public void onPerceptionBegin(byte type, int timestamp) {
	}

	@Override
	public void onPerceptionEnd(byte type, int timestamp) {
	}

	@Override
	public void onException(Exception exception, MessageS2CPerception perception) {
		exception.printStackTrace();
		System.exit(-1);
	}
}