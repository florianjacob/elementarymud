package elementarymud.client.inputparsing.actions;

import elementarymud.client.Client;
import elementarymud.client.inputparsing.Command;
import elementarymud.client.inputparsing.CommandParser;
import elementarymud.client.inputparsing.Word;
import java.util.HashSet;
import java.util.Set;
import marauroa.common.game.RPAction;

/**
 *
 * @author raignarok
 */
class MoveAction implements Action {

	@Override
	public void execute(Command sentence) {
		// unknown local command, sending to server..
		RPAction action = new RPAction();
		action.put("verb", sentence.getVerb().getWord());
		action.put("object", sentence.getObject().getRPObject().get("name"));
		Client.get().send(action);
	}

	@Override
	public Set<String> actionVerbs() {
		Set<String> verbs = new HashSet<String>();
		verbs.add("go");
		return verbs;
	}

	@Override
	public void parse(CommandParser parser, Command command) {
		Word object = command.getObject();
		command.setObject(object);
	}
}
