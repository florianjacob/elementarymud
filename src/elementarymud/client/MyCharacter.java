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

import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.RPObject;

/**
 * MyCharacter objects keep track of the character name the player chose after
 * login, the character RPObject which represents himself, the current zone
 * RPObject the character is in and whether the zone has changed recently.
 * 
 * The MyCharacter object is part of the ZoneObjects.
 * No need to create MyCharacter objects yourself.
 *
 * @author raignarok
 */
public class MyCharacter {
	private static final Logger log = Log4J.getLogger(MyCharacter.class);

	private String name;
	private RPObject myCharacter;
	private RPObject currentZoneObject;
	private boolean zoneChanged = false;

	/**
	 * Only should get called in ZoneObjects.
	 */
	protected MyCharacter() {
	}
			

	/**
	 * Check whether the given RPObject is the RPObject representing the user's character.
	 * When an RPObject is the subclass of the "character" RPClass and has the same name as
	 * the user has chosen as its character by the start, an object is considered to be the
	 * character object. This relies on having unique character names.
	 * 
	 * @param object the RPObject to check whether it's the user's character object
	 * @return true if the given object is the user's character object
	 */
	protected boolean isCharacter(final RPObject object) {
		if (name == null) {
			return false;
		}

		if (object.getRPClass().subclassOf("character")) {
			return name.equalsIgnoreCase(object.get("name"));
		} else {
			return false;
		}
	}	

	/**
	 * Sets the name of the user's character.
	 * Should be only called once after the user has chosen a valid character.
	 * 
	 * @param name the name of the user's character
	 */
	protected void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the chosen user's character name or null if he didn't so far or the choice was invalid
	 */
	public String getName() {
		return name;
	}

	/**
	 * Takes the given RPObject as the new RPObject representing the user's character. 
	 * Does nothing if the provided RPObject isn't the user's character object as
	 * determined by isCharacter(newCharacter) or the provided RPObject is the same
	 * as the current one.
	 * 
	 * @param newCharacter the new RPObject representing the users character
	 */
	public void setCharacter(final RPObject newCharacter) {
		if (isCharacter(newCharacter) && this.myCharacter != newCharacter) {
			myCharacter = newCharacter;
			name = newCharacter.get("name");
		} else {
			log.warn("Character not set because it's not our character.");
		}
	}

	public RPObject getCharacter() {
		return myCharacter;
	}

	public RPObject getZone() {
		return currentZoneObject;
	}

	public void setZone(final RPObject zone) {
		this.currentZoneObject = zone;
		zoneChanged = true;
	}

	public boolean hasChangedZone() {
		return zoneChanged;
	}

	public void zoneChangeHandled() {
		zoneChanged = false;
	}

}
