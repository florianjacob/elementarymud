package elementarymud.client.inputparsing.actions;

import elementarymud.client.inputparsing.CommandScanner;
import java.util.HashSet;
import java.util.Set;
import marauroa.common.game.RPAction;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public class DropAction extends Action {

	@Override
	public Set<String> actionVerbs() {
		Set<String> verbs = new HashSet<>();
		verbs.add("drop");	
		return verbs;
	}

	@Override
	public boolean configure(CommandScanner scanner) {
		RPObject bagItem = scanner.scanForBagItem();
		if (bagItem != null) {
			setTarget(bagItem);	
			return true;
		} else {
			getUI().writeln("Usage: drop <item in your bag>");
			return false;
		}
	}

	@Override
	public RPAction execute() {
		RPAction action = new RPAction();
		action.put("verb", "drop");
		action.put("target", getTargetID().getObjectID());

		return action;
	}
	
}
