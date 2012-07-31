package elementarymud.server;

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
		}
		// in case the object is a player, this sends a sync perception to him
		World.get().requestSync(object);
		
	}

	@Override
	public synchronized RPObject remove(final RPObject.ID id) {
		RPObject object = get(id);		
		if (object instanceof Character) {
			((Character) object).onRemoved(this);
		}

		return super.remove(id);

	}
	
}
