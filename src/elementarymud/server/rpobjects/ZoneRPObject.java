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

package elementarymud.server.rpobjects;

import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public class ZoneRPObject extends MudRPObject {

	private static final String RPCLASSNAME = "zone";

	public ZoneRPObject(String name, String description) {
		super(name, description);
		setRPClass(RPCLASSNAME);
	}

	public ZoneRPObject(RPObject template) {
		super(template);
	}

	public static void generateRPClass() {
		RPClass zoneClass = new RPClass(RPCLASSNAME);
		zoneClass.isA(MudRPObject.getRPClassName());
	}

	public static String getRPClassName() {
		return RPCLASSNAME;
	}
}
