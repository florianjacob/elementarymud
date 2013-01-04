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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public class ZoneObjects {
	
	private final Map<RPObject.ID, RPObject> zoneObjects = new HashMap<>();
	private final MyCharacter myCharacter = new MyCharacter();

	public MyCharacter getMyCharacter() {
		return myCharacter;
	}

	public Map<RPObject.ID, RPObject> getInternalObjectsMap() {
		return zoneObjects;
	}

	public RPObject getObject(final RPObject.ID id) {
		return zoneObjects.get(id);
	}

	public Collection<RPObject> getAllObjects() {
		return Collections.unmodifiableCollection(zoneObjects.values());
	}

	public List<RPObject> getExits() {
		ArrayList<RPObject> exits = new ArrayList<>(zoneObjects.size());
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

	public void receivedMyCharacter(RPObject.ID id) {
		myCharacter.setCharacter(getObject(id));
	}
	
	/**
	 * @return a list of everything excluding players, rooms and exits
	 */
	public List<RPObject> getEntities() {
		ArrayList<RPObject> entities = new ArrayList<>(zoneObjects.size());
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

	/**
	 * Returns a list of players in this room .
	 *
	 * @return the list of players
	 */
	public List<RPObject> getPlayers() {
		ArrayList<RPObject> players = new ArrayList<>(zoneObjects.size());
		for (RPObject object : zoneObjects.values()) {
			if (object.instanceOf(RPClass.getRPClass("character"))) {
				players.add(object);
			}
		}

		if (players.isEmpty()) {
			return Collections.emptyList();
		}

		return Collections.unmodifiableList(players);
	}

	/**
	 * Returns a list of players in this room excluding the given player.
	 *
	 * @param player the player to exclude
	 * @return the list of players
	 */
	public List<RPObject> getPlayersExcluding(RPObject player) {
		ArrayList<RPObject> otherPlayers = new ArrayList<>(zoneObjects.size());
		for (RPObject object : zoneObjects.values()) {
			if (object.instanceOf(RPClass.getRPClass("character"))
					&& !object.getID().equals(player.getID())) {
				otherPlayers.add(object);
			}
		}

		if (otherPlayers.isEmpty()) {
			return Collections.emptyList();
		}

		return Collections.unmodifiableList(otherPlayers);
	}
}
