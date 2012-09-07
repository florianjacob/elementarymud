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
