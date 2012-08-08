package elementarymud.client.inputparsing;

import elementarymud.client.Client;
import elementarymud.client.MyCharacter;
import elementarymud.client.UI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public class LookAction implements Action {

	@Override
	public void execute(Command sentence) {
		UI ui = Client.get().getUI();
		if (!sentence.hasObject()) {
			MyCharacter myCharacter = Client.get().getMyCharacter();
			// just look around in the zone
			RPObject zone = myCharacter.getZone();
			StringBuilder output = new StringBuilder();
			output.append("You are in ").append(zone.get("description")).append(".");
			List<RPObject> exits = Client.get().getExits();
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

			List<RPObject> otherPlayers = getPlayersExcluding(myCharacter.getCharacter());

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

			List<RPObject> entities = Client.get().getEntities();
			if (!entities.isEmpty()) {
				output.append("\n");
				output.append("You see ");
				appendPrettyEntityNameList(output, entities);
				output.append(".");
			}

			ui.writeln(output.toString());
		} else {
			// look at something specific
			RPObject object = sentence.getObject().getRPObject();

			StringBuilder out = new StringBuilder();
			out.append("You see ");

			if (object.instanceOf(RPClass.getRPClass("character"))) {
				out.append(object.get("name")).append(", ").append(object.get("description"));
			} else {
				out.append(object.get("description"));
			}
			out.append(".");
			ui.writeln(out.toString());
		}
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

	private StringBuilder appendDescription(StringBuilder out, RPObject object) {
		if (object.instanceOf(RPClass.getRPClass("character"))) {
			out.append(object.get("name")).append(", ").append(object.get("description"));
		}
		out.append(object.get("description"));
		return out;
	}

	/**
	 * Returns a list of players in this room excluding the given player.
	 *
	 * @param player the player to exclude
	 * @return the list of players
	 */
	private List<RPObject> getPlayersExcluding(RPObject player) {
		MyCharacter myCharacter = Client.get().getMyCharacter();
		Map<RPObject.ID, RPObject> zoneObjects = Client.get().getZoneObjects();

		ArrayList<RPObject> otherPlayers = new ArrayList<RPObject>(zoneObjects.size());
		for (RPObject object : zoneObjects.values()) {
			if (object.instanceOf(RPClass.getRPClass("character"))
					&& !object.getID().equals(myCharacter.getCharacter().getID())) {
				otherPlayers.add(object);
			}
		}

		if (otherPlayers.isEmpty()) {
			return Collections.emptyList();
		}

		return Collections.unmodifiableList(otherPlayers);
	}
}
