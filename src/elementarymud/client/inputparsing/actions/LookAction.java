package elementarymud.client.inputparsing.actions;

import elementarymud.client.MyCharacter;
import elementarymud.client.inputparsing.CommandScanner;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import marauroa.common.game.RPAction;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public class LookAction extends Action {

	@Override
	public RPAction execute() {
		if (!hasTarget()) {
			MyCharacter myCharacter = getZoneObjects().getMyCharacter();
			// just look around in the zone
			RPObject zone = myCharacter.getZone();
			StringBuilder output = new StringBuilder();
			output.append("You are in ").append(zone.get("description")).append(".");
			List<RPObject> exits = getZoneObjects().getExits();
			if (!exits.isEmpty()) {
				output.append("\n");
				if (exits.size() == 1) {
					output.append("There is one obvious exit: ");
				} else {
					output.append("There are ").append(exits.size()).append(" obvious exits: ");
				}
				appendPrettyNameList(output, exits);
				output.append(".");
			}

			List<RPObject> otherPlayers = getZoneObjects().getPlayersExcluding(myCharacter.getCharacter());

			if (!otherPlayers.isEmpty()) {
				output.append("\n");
				if (otherPlayers.size() == 1) {
					output.append("Also in here is ");
				} else {
					output.append("Also in here are ");
				}
				appendPrettyNameList(output, otherPlayers);
				output.append(".");
			}

			List<RPObject> entities = getZoneObjects().getEntities();
			if (!entities.isEmpty()) {
				output.append("\n");
				output.append("You see ");
				appendPrettyEntityNameList(output, entities);
				output.append(".");
			}

			getUI().writeln(output.toString());
		} else {
			// look at something specific
			RPObject target = getTarget();

			StringBuilder out = new StringBuilder();
			out.append("You see ");

			if (target.instanceOf(RPClass.getRPClass("character"))) {
				out.append(target.get("name")).append(", ").append(target.get("description"));
			} else {
				out.append(target.get("description"));
			}
			out.append(".");
			getUI().writeln(out.toString());
		}

		return null;
	}

	/**
	 * Appends the names of the {@code SwordWorldObject}s in the list to the builder, separated by commas, with an "and"
	 * before the final item.
	 *
	 * @param builder the {@code StringBuilder} to append to
	 * @param list the list of items to format
	 */
	private void appendPrettyNameList(StringBuilder builder, List<RPObject> list) {
		if (list.isEmpty()) {
			return;
		}

		int lastIndex = list.size() - 1;
		RPObject last = list.get(lastIndex);

		Iterator<RPObject> it = list.subList(0, lastIndex).iterator();
		if (it.hasNext()) {
			RPObject other = it.next();
			builder.append(other.get("name"));
			while (it.hasNext()) {
				other = it.next();
				builder.append(", ");
				builder.append(other.get("name"));
			}
			builder.append(" and ");
		}
		builder.append(last.get("name"));
	}

	private void appendPrettyEntityNameList(StringBuilder builder, List<RPObject> list) {
		if (list.isEmpty()) {
			return;
		}

		int lastIndex = list.size() - 1;
		RPObject last = list.get(lastIndex);

		Iterator<RPObject> it = list.subList(0, lastIndex).iterator();
		if (it.hasNext()) {
			RPObject other = it.next();
			builder.append("a ").append(other.get("name"));
			while (it.hasNext()) {
				other = it.next();
				builder.append(", ");
				builder.append("a ").append(other.get("name"));
			}
			builder.append(" and ");
		}
		builder.append("a ").append(last.get("name"));
	}

	@Override
	public Set<String> actionVerbs() {
		Set<String> verbs = new HashSet<>();
		verbs.add("look");	
		verbs.add("l");
		return verbs;
	}

	@Override
	public boolean configure(CommandScanner scanner) {
		setTarget(scanner.scanForRPObject());
		return true;
	}
}
