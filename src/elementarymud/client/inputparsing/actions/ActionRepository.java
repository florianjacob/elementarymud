package elementarymud.client.inputparsing.actions;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author raignarok
 */
public class ActionRepository {
	private final Map<String, Action> actionPrototypes = new HashMap<>();
	private static final ActionRepository instance = new ActionRepository();

	public static ActionRepository get() {
		return instance;
	}

	private ActionRepository() {
		registerAction(new LookAction());
		registerAction(new MoveAction());
		registerAction(new SayAction());
		registerAction(new TakeAction());
		registerAction(new DropAction());
		registerAction(new InventoryAction());
	}

	private void registerAction(final Action action) {
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
