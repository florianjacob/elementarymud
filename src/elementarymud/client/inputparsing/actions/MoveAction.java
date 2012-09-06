package elementarymud.client.inputparsing.actions;

import elementarymud.client.Client;
import elementarymud.client.inputparsing.CommandScanner;
import java.util.HashSet;
import java.util.Set;
import marauroa.common.game.RPAction;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
class MoveAction extends Action {

	@Override
	public void execute() {
		// unknown local command, sending to server..
		RPAction action = new RPAction();
		action.put("verb", "go");
		action.put("target", getTarget().get("name"));
		Client.get().send(action);
	}

	@Override
	public Set<String> actionVerbs() {
		Set<String> verbs = new HashSet<>();
		verbs.add("go");
		return verbs;
	}

	@Override
	public boolean configure(CommandScanner scanner) {
		RPObject exit = scanner.scanForExit();
		if (exit != null) {
			setTarget(exit);
			return true;
		} else {
			return false;
		}
	}
}
