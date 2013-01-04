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

package elementarymud.client.inputparsing.actions;

import elementarymud.client.UI;
import elementarymud.client.ZoneObjects;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author raignarok
 */
public class ActionRepository {
	private final Map<String, Action> actionPrototypes = new HashMap<>();
	private UI ui;
	private ZoneObjects zoneObjects;

	public ActionRepository(ZoneObjects zoneObjects, UI ui) {
		this.ui = ui;
		this.zoneObjects = zoneObjects;
		registerAction(new LookAction());
		registerAction(new MoveAction());
		registerAction(new SayAction());
		registerAction(new TakeAction());
		registerAction(new DropAction());
		registerAction(new InventoryAction());
		registerAction(new SerializeAction());
	}

	private void registerAction(final Action action) {
		action.init(zoneObjects, ui);
		for (String verb : action.actionVerbs()) {
			actionPrototypes.put(verb, action);
		}
	}

	public boolean hasAction(final String name) {
		return actionPrototypes.containsKey(name);
	}

	public Action getAction(final String name) {
		if (hasAction(name)) {
			return actionPrototypes.get(name).clone();
		} else {
			return null;
		}
	}
}
