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

	private void registerAction(Action action) {
		for (String verb : action.actionVerbs()) {
			actions.put(verb, action);
		}
	}

	public boolean hasAction(String name) {
		return actions.containsKey(name);
	}

	public Action getAction(String name) {
		return actions.get(name);
	}
}
