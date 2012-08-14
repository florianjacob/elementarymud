package elementarymud.client.inputparsing.actions;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author raignarok
 */
public class ActionRepository {
	private final Map<String, Action> actions = new HashMap<String, Action>();
	private static final ActionRepository instance = new ActionRepository();

	public static ActionRepository get() {
		return instance;
	}

	private ActionRepository() {
		registerAction(new LookAction());
		registerAction(new MoveAction());
		registerAction(new SayAction());
	}

	private void registerAction(final Action action) {
		for (String verb : action.actionVerbs()) {
			actions.put(verb, action);
		}
	}

	public boolean hasAction(final String name) {
		return actions.containsKey(name);
	}

	public Action getAction(final String name) {
		return actions.get(name);
	}
}
