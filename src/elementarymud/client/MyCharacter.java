/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementarymud.client;

import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public class MyCharacter {
	private String name;
	private RPObject myCharacter;
	private RPObject currentZoneObject;
	private boolean zoneChanged;

	public boolean isCharacter(RPObject object) {
		if (name == null) {
			return false;
		}

		if (object.getRPClass().subclassOf("character")) {
			return name.equalsIgnoreCase(object.get("name"));
		} else {
			return false;
		}
	}	

	public void setCharacterName(String name) {
		this.name = name;
	}

	public String getCharacterName() {
		return name;
	}

	public void setCharacter(RPObject newCharacter) {
		if (this.myCharacter != newCharacter) {
			myCharacter = newCharacter;
			name = newCharacter.get("name");
		}
	}

	public RPObject getCharacter() {
		return myCharacter;
	}

	public RPObject getZone() {
		return currentZoneObject;
	}

	public void setZone(RPObject zone) {
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
