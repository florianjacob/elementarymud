package elementarymud.server;

import elementarymud.server.rpobjects.Character;
import elementarymud.server.rpobjects.Exit;
import elementarymud.server.rpobjects.ZoneRPObject;
import java.util.LinkedList;
import java.util.List;
import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.RPObject;
import marauroa.server.game.rp.MarauroaRPZone;

/**
 *
 * @author raignarok
 */
public class RPZone extends MarauroaRPZone {
	private static final Logger log = Log4J.getLogger(RPZone.class);

	private ZoneRPObject zoneRPObject;

	private List<Exit> exits = new LinkedList<Exit>();

	public RPZone(String zoneid, String name, String description) {
		super(zoneid);

		zoneRPObject = new ZoneRPObject(name, description);
		add(zoneRPObject);
	}

	@Override
	public synchronized void add(RPObject object) {
		assignRPObjectID(object);
		super.add(object);
		if (object instanceof Character) {
			((Character) object).onAdded(this);
		} else if (object instanceof Exit) {
			exits.add((Exit) object);	
		}
		// in case the object is a player, this sends a sync perception to him
		World.get().requestSync(object);
	}

	@Override
	public synchronized RPObject remove(final RPObject.ID id) {
		RPObject object = get(id);		
		if (object instanceof Character) {
			((Character) object).onRemoved(this);
		} else if (object instanceof Exit) {
			exits.remove((Exit) object);
		}

		return super.remove(id);
	}

	public ZoneRPObject getZoneObject() {
		return zoneRPObject;
	}

	public boolean hasExit(String exitName) {
		for (Exit exit : exits) {
			if (exit.getName().equals(exitName)) {
				return true;
			}
		}
		return false;
	}

	public Exit getExit(String exitName) {
		for (Exit exit : exits) {
			if (exit.getName().equals(exitName)) {
				return exit;
			}
		}
		return null;
	}

}
