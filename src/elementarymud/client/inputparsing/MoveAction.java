package elementarymud.client.inputparsing;

import elementarymud.client.Client;
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
		action.put("remainder", sentence.getObject().getWord());
		Client.get().send(action);
	}
}
