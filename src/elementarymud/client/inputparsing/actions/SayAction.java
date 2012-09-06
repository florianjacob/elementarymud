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
class SayAction extends Action {

	private String usedVerb;
	private String wordsToSpeak;

	@Override
	public void execute() {
		RPAction action = new RPAction();
		action.put("verb", usedVerb);

		if (hasTarget()) {
			action.put("target", getTargetID().getObjectID());
		}
		action.put("words", wordsToSpeak);

		Client.get().send(action);
	}

	@Override
	public Set<String> actionVerbs() {
		Set<String> verbs = new HashSet<>();
		verbs.add("say");
		verbs.add("sayto");
		verbs.add("tell");
		return verbs;

	}

	@Override
	public boolean configure(CommandScanner scanner) {
		usedVerb = scanner.getVerb();
		if (!usedVerb.equals("say")) {
			// tell and sayto (the other say actions) have a target object
			RPObject target = scanner.scanForPlayer();
			if (target != null) {
				setTarget(target);
				wordsToSpeak = scanner.getRemainder();
				return true;
			} else {
				return false;
			}
		} else {
			wordsToSpeak = scanner.getInputWithoutVerb();
			return true;
		}
	}
}
