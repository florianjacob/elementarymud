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

import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.Definition;
import marauroa.common.game.IRPZone;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public class Exit extends MudRPObject {
	private static final Logger log = Log4J.getLogger(Exit.class);

	private static final String RPCLASSNAME = "exit";
	private static final String TARGETZONEID = "targetzoneid";

	public Exit(String name, String shortName, String description, IRPZone.ID targetZoneId) {
		super(name, shortName, description);
		setRPClass(RPCLASSNAME);
		put(TARGETZONEID, targetZoneId.getID());
	}

	public Exit(RPObject template) {
		super(template);
	}

	public IRPZone.ID getTargetZoneId() {
		return new IRPZone.ID(get(TARGETZONEID));
	}

	public static void generateRPClass() {
		RPClass exit = new RPClass(RPCLASSNAME);
		exit.isA(MudRPObject.getRPClassName());
		exit.addAttribute(TARGETZONEID, Definition.Type.STRING);
	}

	public static String getRPClassName() {
		return RPCLASSNAME;
	}
}
