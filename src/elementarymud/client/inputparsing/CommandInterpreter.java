package elementarymud.client.inputparsing;

import elementarymud.client.Client;
import elementarymud.client.MyCharacter;
import elementarymud.client.UI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import marauroa.common.game.RPAction;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public class CommandInterpreter {

	private static final Map<String, Action> actions = new HashMap<String, Action>();

	static {
		actions.put("look", new LookAction());
		actions.put("go", new MoveAction());
		actions.put("say", new SayAction());
	}

	public Action getAction(String name) {
		return actions.get(name);
	}

	public void onInput(String input) {
		Command sentence = Command.parseCommand(CommandScanner.scan(input), input);

		Action action = sentence.getVerb().getAction();
		action.execute(sentence);
	}



}
