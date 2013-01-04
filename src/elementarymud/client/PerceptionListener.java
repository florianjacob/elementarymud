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

import elementarymud.client.inputparsing.actions.ActionRepository;
import elementarymud.client.inputparsing.actions.LookAction;
import marauroa.client.net.IPerceptionListener;
import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPEvent;
import marauroa.common.game.RPObject;
import marauroa.common.net.message.MessageS2CPerception;

public class PerceptionListener implements IPerceptionListener {
	private static final Logger log = Log4J.getLogger(PerceptionListener.class);

	private final ZoneObjects zoneObjects;
	private final MyCharacter myCharacter;
	private final UI ui;
	private final ActionRepository actions;

	public PerceptionListener(ZoneObjects zoneObjects, UI ui, ActionRepository actions) {
		this.zoneObjects = zoneObjects;
		this.myCharacter = zoneObjects.getMyCharacter();
		this.ui = ui;
		this.actions = actions;
	}

	@Override
	public boolean onAdded(final RPObject object) {
		log.info("onAdded: " + object);
		if (myCharacter.isCharacter(object)) {
			myCharacter.setCharacter(object);
		} else if (object.instanceOf(RPClass.getRPClass("zone"))) {
			myCharacter.setZone(object);
		} else if (object.instanceOf(RPClass.getRPClass("character")) && !myCharacter.hasChangedZone()) {
			ui.writeln(object.get("name") + " entered the " + myCharacter.getZone().get("name") + ".");
		}
		return false;
	}

	@Override
	public boolean onModifiedAdded(final RPObject object, final RPObject changes) {
		log.info("onModifiedAdded: " + object + " changes: " + changes);
		for (RPEvent event : changes.events()) {
			if (event.getName().equals("public_text")) {
				String prefix = myCharacter.isCharacter(object) ? "You say: " : object.get("name") + " says: ";
				ui.writeln(prefix + event.get("text"));
			}
		}
		return false;
	}

	@Override
	public boolean onModifiedDeleted(final RPObject object, final RPObject changes) {
		log.info("onModifiedDeleted: " + object + " changes: " + changes);
		return false;
	}

	@Override
	public boolean onDeleted(final RPObject object) {
		log.info("onDeleted: " + object);
		if (object.instanceOf(RPClass.getRPClass("character"))) {
			RPObject leavingCharacter = zoneObjects.getObject(object.getID());
			ui.writeln(leavingCharacter.get("name") + " left the "
					+ myCharacter.getZone().get("name") + ".");
		}
		return false;
	}

	@Override
	public boolean onMyRPObject(final RPObject added, final RPObject deleted) {
		// onMyRPObject gets called everytime our own RPObject changes so we know that it changed
		// but this happens after onAdded() was already called with our RPObject and all public attributes
		// added and deleted don't contain any public attributes except id and zone
		// they only contain the private added and deleted attributes / events
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
			zoneObjects.receivedMyCharacter(id);
			log.info("onMyRPObject: added: " + added + " deleted: " + deleted);
		}

		if (myCharacter.hasChangedZone()) {
			// execute a look command everytime we get in a new zone and the zone is synced completely
			actions.getAction("look").execute();
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
