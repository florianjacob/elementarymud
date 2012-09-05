package elementarymud.client.inputparsing.actions;

import elementarymud.client.Client;
import elementarymud.client.MyCharacter;
import elementarymud.client.ZoneObjects;
import elementarymud.client.inputparsing.CommandScanner;
import java.util.HashSet;
import java.util.Set;
import marauroa.common.game.RPObject;
import marauroa.common.game.RPSlot;

/**
 *
 * @author raignarok
 */
public class InventoryAction extends Action {

	@Override
	public Set<String> actionVerbs() {
		Set<String> verbs = new HashSet<>();
		verbs.add("inventory");
		verbs.add("i");
		return verbs;
	}

	@Override
	public boolean configure(CommandScanner scanner) {
		// nothing to do here..
		return true;
	}

	@Override
	public void execute() {
		MyCharacter myCharacter = ZoneObjects.get().getMyCharacter();
		RPSlot bag = myCharacter.getCharacter().getSlot("bag");
		StringBuilder builder = new StringBuilder();
		builder.append("You have with you: \n");
		for (RPObject object : bag) {
			builder.append(object.get("description")).append("\n");
		}
		Client.get().getUI().write(builder.toString());
	}
	
}