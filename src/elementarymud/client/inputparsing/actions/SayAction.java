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
class SayAction implements Action {

	@Override
	public void execute(Command command) {
		RPAction action = new RPAction();
		String verb = command.getVerb().getWord();
		String remainder = command.getRemainder();
		action.put("verb", verb);

		if (command.hasObject()) {
			action.put("object", command.getObject().getRPObject().getID().getObjectID());
		}
		action.put("remainder", remainder);

		Client.get().send(action);
	}

	@Override
	public Set<String> actionVerbs() {
		Set<String> verbs = new HashSet<String>();
		verbs.add("say");
		verbs.add("sayto");
		verbs.add("tell");
		return verbs;

	}

	@Override
	public void parse(CommandParser parser, Command command) {
		if (!command.getVerb().getWord().equals("say")) {
			// tell and sayto (the other say actions) have a target object
			Word object = parser.nextObject();
			command.setObject(object);
			command.setRemainder(parser.getUnparsedRemainder(object));
		} else {
			command.setRemainder(parser.getUnparsedRemainder(command.getVerb()));
		}
	}
}
