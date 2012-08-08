package elementarymud.client.inputparsing;

import elementarymud.client.Client;
import marauroa.common.game.RPAction;

/**
 *
 * @author raignarok
 */
class SayAction implements Action {

	@Override
	public void execute(Command sentence) {
		RPAction action = new RPAction();
		action.put("verb", sentence.getVerb().getWord());
		action.put("remainder", sentence.getRemainder());

		Client.get().send(action);
	}
}
